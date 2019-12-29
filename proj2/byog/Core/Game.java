package byog.Core;

import java.awt.Font;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;



/**
 * @author CS61B / Emanuel Aguirre
 */
public class Game {

    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. These are for the world (tile array). */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    /* Size available for the HUD (head-up display). */
    private static final int HUD_HEIGHT = 2;
    /* Inner width and height of the game window. */
    private static final int WINDOW_WIDTH = WIDTH;
    private static final int WINDOW_HEIGHT = HEIGHT + HUD_HEIGHT;
    /* Tiles to be used for the floor, walls, and player. */
    private static final TETile FLOOR_TILE = Tileset.FLOOR;
    private static final TETile WALL_TILE = Tileset.WALL;
    private static final TETile PLAYER_TILE = Tileset.PLAYER;
    /* Name of the file where the state of a saved game is to be stored. */
    private static final String STATE_FILENAME = "./game.ser";
    /* Maximum and minimum room sides sizes, and difference between width and height for rooms. */
    private static final int MIN_SIDE = 4;
    private static final int MAX_SIDE = 4;
    private static final int DELTA_WIDTH_HEIGHT = 1;
    /* Maximum number of rooms to draw. */
    private static final int MAX_ROOMS = 30;
    /* Maximum number of tries when adding a random room that does not overlaps with others. */
    private static final int MAX_TRIES = 30;

    /* Pause in milliseconds for general purpose. */
    private static int PAUSE_250_MILLISECONDS = 250;

    /* Size of characters for each type of text displayed at the game window. */
    private static final int TITLE_FONT_SIZE = 40;
    private static final int INITIAL_COMMANDS_FONT_SIZE = 30;
    private static final int HUD_FONT_SIZE = 16;
    /* Messages to be displayed at the game window. */
    private static final String TITLE = "CS61B: THE GAME";
    private static final String INITIAL_COMMAND_NEW_GAME = "New Game (N)";
    private static final String INITIAL_COMMAND_LOAD_GAME = "Load Game (L)";
    private static final String INITIAL_COMMAND_QUIT = "Quit(Q)";

    /* The array of tiles on which the game world is to be drawn. */
    private TETile[][] gameWorld;
    /* Coordinates on which the player can move (these points represent rooms and hallways). */
    private List<Point> coordinates = null;
    /* The walls to be drawn around the traversable areas (floor of rooms and hallways). */
    private Walls walls = null;
    /* Player in the world. */
    private Player player;
    /* Game state, used mainly for saving the game state. */
    private GameState gameState;


    public Game() {
        gameWorld = new TETile[WIDTH][HEIGHT];
        // THIS LINE IS ONLY REMOVED TO BE ABLE TO RUN WITH THE AUTOGRADER
        //ter.initialize(WIDTH, HEIGHT);
        initializeWorldBackground(gameWorld, Tileset.NOTHING);
        gameState = new GameState(gameWorld, PLAYER_TILE);
    }


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WINDOW_WIDTH, WINDOW_HEIGHT);
        RandomWorldGenerator rwg = null;
        String validFirstCommands = "" + Keys.NEW_GAME + Keys.LOAD_GAME + Keys.QUIT_SAVE;
        char command = 0;
        while (validFirstCommands.indexOf(command) == -1) {
            displayInitialMenu();
            command = readKey();
        }

        if (command == Keys.NEW_GAME) {
            Long seed = readSeed();
            Random random = new Random(seed);
            rwg = new RandomWorldGenerator(gameWorld, FLOOR_TILE, WALL_TILE, random);
            coordinates = rwg.generateAllowedCoordinates(MIN_SIDE, MAX_SIDE,
                    DELTA_WIDTH_HEIGHT, MAX_ROOMS, MAX_TRIES);
            walls = rwg.generateWalls(coordinates);
            player = new Player(
                    coordinates.get(RandomUtils.uniform(random, 0, coordinates.size())),
                    coordinates, PLAYER_TILE, gameWorld);
        } else if (command == Keys.LOAD_GAME) {
            if ((gameState = GameState.load(STATE_FILENAME)) == null) {
                quit("THERE IS NO SAVED GAME, QUITTING...");
            }
            rwg = new RandomWorldGenerator(gameWorld, FLOOR_TILE, WALL_TILE, new Random(0));
            gameState.setWorld(gameWorld);
            gameState.setPlayerTile(PLAYER_TILE);
            coordinates = gameState.getAllowedPoints();
            walls = rwg.generateWalls(coordinates);
            player = gameState.getPlayer();
        } else {
            quit("QUITTING...");
        }

        play(gameWorld, player);
        quit("QUITTING...");
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * Example: running first "N333DDDDDDDDDDDDDDDDDDDD:Q", should move right and save and quit;
     * then running "LWWWWWWW", should load the previous saved game and move up.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        input = input.toUpperCase();
        char firstCommand = extractFirstCommand(input).charAt(0);
        long seed = extractSeed(input);
        String commands = extractCommands(input);

        Random random = new Random(seed);
        RandomWorldGenerator rwg =
                new RandomWorldGenerator(gameWorld, FLOOR_TILE, WALL_TILE, random);

        if (firstCommand == Keys.NEW_GAME) {
            coordinates = rwg.generateAllowedCoordinates(MIN_SIDE, MAX_SIDE,
                    DELTA_WIDTH_HEIGHT, MAX_ROOMS, MAX_TRIES);
            walls = rwg.generateWalls(coordinates);
            player = new Player(
                    coordinates.get(RandomUtils.uniform(random, 0, coordinates.size())),
                    coordinates, PLAYER_TILE, gameWorld);
        } else if (firstCommand == Keys.LOAD_GAME) {
            gameState = GameState.load(STATE_FILENAME);
            gameState.setWorld(gameWorld);
            gameState.setPlayerTile(PLAYER_TILE);
            coordinates = gameState.getAllowedPoints();
            player = gameState.getPlayer();
            walls = rwg.generateWalls(coordinates);
        } else {
            return gameWorld;
        }

        movePlayerWithString(commands, gameWorld, player);
        drawAtCoordinates(coordinates, gameWorld, FLOOR_TILE);
        walls.draw();
        player.draw();
        // THIS LINE IS ONLY REMOVED TO BE ABLE TO RUN WITH THE AUTOGRADER
        //ter.renderFrame(world);
        return gameWorld;
    }

    /**
     * Plays the game using the keyboard.
     */
    private void play(TETile[][] world, Player pl) {
        char c = 0;
        while (c != Keys.QUIT_SAVE) {
            renderGamePlay();
            renderMousePositionInformation();
            c = readKey();
            switch (c) {
                case Keys.UP:
                    pl.moveUp();
                    break;
                case Keys.DOWN:
                    pl.moveDown();
                    break;
                case Keys.LEFT:
                    pl.moveLeft();
                    break;
                case Keys.RIGHT:
                    pl.moveRight();
                    break;
                case Keys.QUIT_SAVE:
                    break;
                default:
                    break;
            }
        }
        displayMessage("SAVE GAME? (Y/N)");
        while (true) {
            c = readKey();
            if (c == Keys.YES) {
                gameState.setState(coordinates, walls.getPoints(), player.position());
                GameState.save(gameState, STATE_FILENAME);
                break;
            } else if (c == Keys.NO) {
                break;
            }
        }
    }

    /**
     * Renders a frame of the game at the current state and the HUD.
     */
    private void renderGamePlay() {
        drawAtCoordinates(coordinates, gameWorld, FLOOR_TILE);
        walls.draw();
        player.draw();
        ter.renderFrame(gameWorld);
    }

    /**
     * Moves player around the world, using the commands (characters that correspond to keys)
     * passed as parameter.
     * @param movements are the set of direction commands to move the player around.
     * @param world is the world in which the player moves.
     * @param pl is the player.
     */
    private void movePlayerWithString(String movements, TETile[][] world, Player pl) {
        for (char c : movements.toCharArray()) {
            switch (c) {
                case Keys.UP:
                    pl.moveUp();
                    break;
                case Keys.DOWN:
                    pl.moveDown();
                    break;
                case Keys.LEFT:
                    pl.moveLeft();
                    break;
                case Keys.RIGHT:
                    pl.moveRight();
                    break;
                case Keys.PRE_QUIT_SAVE:
                    int i = movements.indexOf(c);
                    if (movements.charAt(i + 1) == Keys.QUIT_SAVE) {
                        gameState.setState(coordinates, walls.getPoints(), player.position());
                        GameState.save(gameState, STATE_FILENAME);
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Initializes the background of the world to be drawn.
     * @param world is the array of TETile on which the world is to be drawn.
     * @param background is the specific tile to be used for the background.
     */
    private void initializeWorldBackground(TETile[][] world, TETile background) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = background;
            }
        }
    }

    /**
     * Helper method that draws the tiles passed as parameters at the points passed as parameters.
     * @param points are the points at which to draw the tiles.
     * @param world is the world that has the coordinate points on which to draw.
     * @param tile is the type of tile to draw.
     */
    private void drawAtCoordinates(List<Point> points, TETile[][] world, TETile tile) {
        for (Point p : points) {
            world[p.x()][p.y()] = tile;
        }
    }

    /**
     * @return the first command for playing with input String.
     */
    private String extractFirstCommand(String input) {
        return input.substring(0, 1);
    }

    /**
     * @return the seed for playing with input String.
     */
    private long extractSeed(String input) {
        input = input.substring(1);
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(input);
        return (matcher.find()
                ? Long.parseLong(input.substring(matcher.start(), matcher.end()))
                : 0);
    }

    /**
     * @return the keystrokes for playing with input String.
     */
    private String extractCommands(String input) {
        input = input.substring(1);
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(input);
        int start = matcher.find() ? matcher.end() : 0;
        return input.substring(start);
    }

    /**
     * Displays the initial menu when playing with keyboard.
     */
    private void displayInitialMenu() {
        final int verticalSeparation = 2;
        Font currentFont = StdDraw.getFont();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.setFont(currentFont.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        StdDraw.text(WINDOW_WIDTH / 2, WINDOW_HEIGHT * 9 / 10, TITLE);

        StdDraw.setFont(currentFont.deriveFont(Font.BOLD, INITIAL_COMMANDS_FONT_SIZE));
        StdDraw.text(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2 + verticalSeparation,
                INITIAL_COMMAND_NEW_GAME);
        StdDraw.text(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2,
                INITIAL_COMMAND_LOAD_GAME);
        StdDraw.text(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2 - verticalSeparation,
                INITIAL_COMMAND_QUIT);

        StdDraw.setFont(currentFont);
        StdDraw.show();
    }

    /**
     * Displays a message and exits.
     * @param message to display when quitting.
     */
    private void quit(String message) {
        displayMessage(message);
        StdDraw.pause(4 * PAUSE_250_MILLISECONDS);
        System.exit(0);
    }

    /**
     * Displays a message to the player.
     * @param message is the message to dos play at the game window.
     */
    private void displayMessage(String message) {
        Font currentFont = StdDraw.getFont();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(currentFont.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        StdDraw.text(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, message);
        StdDraw.setFont(currentFont);
        StdDraw.show();
    }

    /**
     * Promts the user to enter a seed.
     * @return the seed entered by the user
     */
    private Long readSeed() {
        Long seed = null;
        while (true) {
            displayMessage("ENTER SEED");
            seed = readSeedFromKeyboard();
            if (seed != null) {
                break;
            } else {
                displayMessage("INVALID SEED");
                StdDraw.pause(4 * PAUSE_250_MILLISECONDS);
            }
        }
        return seed;
    }

    /**
     * Reads the seed to start a new game played by keyboard.
     * @return the seed entered by the user or -1 if the seed is not a valid integer.
     */
    private Long readSeedFromKeyboard() {
        String seed = "";
        while (true) {
            char key = readKey();
            if (key == 0) {
                continue;
            } else if (key == '\n') {
                break;
            }
            seed = seed + key;
            displayMessage("SEED: " + seed);
        }
        try {
            return Long.parseLong(seed);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Reads a key entered by the user using the keyboard.
     * @return a single character read from the keyboard, or 0 if no key was read.
     */
    private char readKey() {
        return StdDraw.hasNextKeyTyped()
                ? java.lang.Character.toUpperCase(StdDraw.nextKeyTyped())
                : 0;
    }

    /**
     * Reads the current position of the mouse on the game window and sets a value
     *  indicating what type of tile is below the mouse pointer.
     */
    private void renderMousePositionInformation() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String message = "Nothing";

        if (x < 0 || y < 0 || WIDTH <= x || HEIGHT <= y) {
            message = "Nothing";
        } else if (x == player.position().x() && y == player.position().y()) {
            message = "Player";
        } else if (gameWorld[x][y] == FLOOR_TILE) {
            message = "Floor";
        } else if (gameWorld[x][y] == WALL_TILE) {
            message = "Wall";
        }

        Font currentFont = StdDraw.getFont();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(currentFont.deriveFont(Font.BOLD, HUD_FONT_SIZE));
        StdDraw.textLeft(WINDOW_WIDTH / 60, HEIGHT + ((float) HUD_HEIGHT) / 2, message);
        StdDraw.line(0, HEIGHT, WIDTH, HEIGHT);
        StdDraw.setFont(currentFont);
        StdDraw.show();
    }

}

package byog.Core;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;



public class Game {

    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
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
     * TODO
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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

}

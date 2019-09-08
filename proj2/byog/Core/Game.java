package byog.Core;

import java.util.List;
import java.util.Random;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;



public class Game {

    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    /* Game control keys. */
    public static final char NEW_GAME = 'N';
    public static final char LOAD_GAME = 'L';
    public static final char PRE_QUIT_SAVE = ':';
    public static final char QUIT_SAVE = 'Q';
    /* Character direction keys. */
    public static final char UP = 'W';
    public static final char DOWN = 'S';
    public static final char LEFT = 'A';
    public static final char RIGHT = 'D';
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

    /* Pseudo-random number generator for generating the world. */
    private Random random = new Random();
    /* Random world generator to generate a random world when a new game is started. */
    private RandomWorldGenerator rwg;
    /* Coordinates on which the player can move (these points represent rooms and hallways). */
    private List<Point> coordinates = null;
    /* The walls to be drawn around the traversable areas (floor of rooms and hallways). */
    private Walls walls = null;
    /* Player in the world. */
    private Player player;
    /* Game state, used mainly for saving the game state. */
    private GameState gameState;


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
        long seed = 0;
        String commands = null;

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        // THIS LINE IS ONLY REMOVED TO BE ABLE TO RUN WITH THE AUTOGRADER
        //ter.initialize(WIDTH, HEIGHT);
        initializeWorldBackground(world, Tileset.NOTHING);
        gameState = new GameState(world, PLAYER_TILE);

        char firstCommand = input.charAt(0);
        if (firstCommand == NEW_GAME) {
            String numberRegex = "\\d+";
            String[] parts = input.split(numberRegex);
            /* The following 3 may raise exceptions, but Style Checker doesn't allow to catch. */
            int seedStart = input.indexOf(parts[0]) + 1;
            int seedEnd = parts.length == 2 ? input.indexOf(parts[1]) : input.length();
            commands = parts.length == 2 ? parts[1] : "";
            seed = Long.parseLong(input.substring(seedStart, seedEnd));
            random = new Random(seed);
            rwg = new RandomWorldGenerator(world, FLOOR_TILE, WALL_TILE, random);
            coordinates = rwg.generateAllowedCoordinates(MIN_SIDE, MAX_SIDE,
                    DELTA_WIDTH_HEIGHT, MAX_ROOMS, MAX_TRIES);
            walls = rwg.generateWalls(coordinates);
            player = new Player(
                    coordinates.get(RandomUtils.uniform(random, 0, coordinates.size())),
                    coordinates, PLAYER_TILE, world);
        } else if (firstCommand == LOAD_GAME) {
            rwg = new RandomWorldGenerator(world, FLOOR_TILE, WALL_TILE, random);
            commands = input.substring(1);
            gameState = GameState.load(STATE_FILENAME);
            gameState.setWorld(world);
            gameState.setPlayerTile(PLAYER_TILE);
            coordinates = gameState.getAllowedPoints();
            player = gameState.getPlayer();
            walls = rwg.generateWalls(coordinates);
        } else {
            return world;
        }

        movePlayerWithString(commands, world, player);
        drawAtCoordinates(coordinates, world, FLOOR_TILE);
        walls.draw();
        player.draw();
        // THIS LINE IS ONLY REMOVED TO BE ABLE TO RUN WITH THE AUTOGRADER
        //ter.renderFrame(world);
        return world;
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
                case UP:
                    pl.moveUp();
                    break;
                case DOWN:
                    pl.moveDown();
                    break;
                case LEFT:
                    pl.moveLeft();
                    break;
                case RIGHT:
                    pl.moveRight();
                    break;
                case PRE_QUIT_SAVE:
                    int i = movements.indexOf(c);
                    if (movements.charAt(i + 1) == QUIT_SAVE) {
                        gameState.setAllowedPoints(coordinates);
                        gameState.setWallsPoints(walls.getPoints());
                        gameState.setPlayerPosition(player.position());
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

}

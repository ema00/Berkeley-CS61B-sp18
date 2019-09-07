package byog.Core;

import java.util.ArrayList;
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
    private static final String STATE_FILENAME = "./world.ser";
    /* Maximum and minimum room sides sizes, and difference between width and height for rooms. */
    private static final int MIN_SIDE = 4;
    private static final int MAX_SIDE = 4;
    private static final int DELTA_WIDTH_HEIGHT = 1;
    /* Maximum number of rooms to draw. */
    private static final int MAX_ROOMS = 30;
    /* Maximum number of tries when adding a random room that does not overlaps with others. */
    private static final int MAX_TRIES = 30;

    /* Pseudo-random number generator for generating the world. */
    private Random random;
    /* Random world generator to generate a random world when a new game is started. */
    private RandomWorldGenerator rwg;
    /* Coordinates on which the player can move (these points represent rooms and hallways). */
    private List<Point> allowedCoordinates = null;
    /* Player in the world. */
    private Player player;
    /* Game state, used mainly for saving the game state. */
    //private GameState gameState;


    /**
     * TODO
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        /*
        * EXPERIMENTAL WORLD GENERATION GOES HERE TO ALLOW RENDERING WITHOUT INPUT STRING
        */
        play();
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
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        /* TODO, fill out this method to run the game using the input passed in, */
        /* TODO, add commands for starting new game, saving and quitting. */
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        input = input.toUpperCase();
        long seed = 0;
        String commands = null;
        List<Room> rooms = null;
        List<Hallway> hallways = null;
        Walls walls = null;

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorldBackground(world, Tileset.NOTHING);
        //gameState = new GameState(world, PLAYER_TILE, FLOOR_TILE, WALL_TILE);

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
            rooms = rwg.generateRoomsNoOverlap(MIN_SIDE, MAX_SIDE, DELTA_WIDTH_HEIGHT,
                    MAX_ROOMS, MAX_TRIES);
            hallways = rwg.generateHallways(rooms);
            allowedCoordinates = rwg.getCoordinates(rooms, hallways);
            walls = rwg.generateWalls(allowedCoordinates);
            player = new Player(
                    allowedCoordinates.get(
                            RandomUtils.uniform(random, 0, allowedCoordinates.size())),
                    allowedCoordinates, PLAYER_TILE, world);
        } else if (firstCommand == LOAD_GAME) {
            commands = input.substring(1);
            //TODO, implement save and load game
            //gameState.load(STATE_FILENAME);
            //allowedCoordinates = gameState.getAllowedCoordinates();
            //player = gameState.getPlayer();
            walls = rwg.generateWalls(allowedCoordinates);
        } else {
            return world;
        }

        movePlayerWithString(commands, world, player);

        drawRooms(rooms);
        drawHallways(hallways);
        walls.draw();
        player.draw();

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
                default:
                    break;
            }
        }
    }

    /**
     * JUST A TEMPORARY METHOD TO TEST THE WORLD GENERATION, THAT IS: ROOMS AND HALLWAYS.
     */
    private void play() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        initializeWorldBackground(world, Tileset.NOTHING);

        random = new Random();
        rwg = new RandomWorldGenerator(world, FLOOR_TILE, WALL_TILE, random);
        //Room[] rooms = rwg.generateRooms(5, 6, 1, 13);
        List<Room> rooms = rwg.generateRoomsNoOverlap(
                MIN_SIDE, MAX_SIDE, DELTA_WIDTH_HEIGHT, MAX_ROOMS, MAX_TRIES);
        List<Hallway> hallways = rwg.generateHallways(rooms);
        Walls walls = rwg.generateWalls(rooms, hallways);

        allowedCoordinates = new ArrayList<>();
        for (Room room : rooms) {
            allowedCoordinates.addAll(room.getPoints());
        }
        for (Hallway hallway : hallways) {
            allowedCoordinates.addAll(hallway.getPoints());
        }
        player =  new Player(allowedCoordinates.get(0), allowedCoordinates, PLAYER_TILE, world);

        drawRooms(rooms);
        drawHallways(hallways);
        walls.draw();
        player.draw();

        ter.renderFrame(world);

        // Y SIGUE...
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
     * Helper method that draws the generated rooms in the world.
     * @param rooms a non null array of rooms to be drawn on the world.
     */
    private void drawRooms(List<Room> rooms) {
        for (Room room : rooms) {
            room.draw();
        }
    }

    /**
     * Helper method that draws the generated hallways in the world.
     * @param hallways a non null array of hallways to be drawn on the world.
     */
    private void drawHallways(List<Hallway> hallways) {
        for (Hallway hallway : hallways) {
            if (hallway != null) {
                hallway.draw();
            }
        }
    }

}

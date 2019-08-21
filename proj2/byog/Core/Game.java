package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Arrays;
import java.util.Random;



public class Game {

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        //////////////////////////////////////////////////////////////////////////////////
        // EXPERIMENTAL WORLD GENERATION GOES HERE TO ALLOW RENDERING WITHOUT INPUT STRING
        //////////////////////////////////////////////////////////////////////////////////
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
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    /**
     * JUST A TEMPORARY METHOD TO TEST THE WORLD GENERATION, THAT IS: ROOMS AND HALLWAYS.
     */
    private void play() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        initializeWorldBackground(world, Tileset.NOTHING);

        Room[] rooms = generateRandomRooms(4, 8, 2, 12, world,
                Tileset.FLOOR, Tileset.WALL, new Random());

        drawRooms(rooms);
        ter.renderFrame(world);

        // Y ACÁ SIGUE...
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
     * Generates random rooms in the world.
     * No collision detection between rooms, which means that room floors can be overlapping.
     * @param sideMin is the minimum dimension for the width or height of a room.
     * @param sideMax is the maximum dimension for the width or height of a room.
     * @param deltaWH is the maximum allowable difference between the width and height of any room.
     * @param max the number of rooms to draw.
     * @param world is the world on which the rooms are to be drawn.
     * @param floor is the type of TETile to be used in the floor of the rooms.
     * @param wall is the type of TETile to be used in the wall of the rooms.
     * @param random is a random generator class instance.
     * @return an array of Room objects placed within the bounds of the world.
     */
    private Room[] generateRandomRooms(int sideMin, int sideMax, int deltaWH, int max, TETile[][] world, TETile floor,
                                      TETile wall, Random random) {
        final int WALL_SIZE = 1;

        if (sideMax + deltaWH + 2 * WALL_SIZE >= world.length || sideMax + deltaWH + 2 * WALL_SIZE >= world[0].length) {
            throw new RuntimeException("Room size is too big for world size.");
        }

        int[] widths = new int[max];
        int[] heights = new int[max];
        for (int i = 0; i < max; i++) {
            widths[i] = RandomUtils.uniform(random, sideMin, sideMax + 1);
        }
        Arrays.sort(widths);    // sorted in ascending order, so the biggest are placed first
        for (int i = 0; i < max; i++) {
            heights[i] = widths[i] + RandomUtils.uniform(random, -deltaWH, deltaWH + 1);
        }

        int xMin = WALL_SIZE;
        int yMin = WALL_SIZE;
        int xMax = world.length - (sideMax + deltaWH + WALL_SIZE);
        int yMax = world[0].length - (sideMax + deltaWH + WALL_SIZE);
        Room[] rooms = new Room[max];
        for (int i = max - 1; i >= 0; i--) {
            int x = RandomUtils.uniform(random, xMin, xMax);
            int y = RandomUtils.uniform(random, yMin, yMax);
            rooms[i] = new Room(x, y, widths[i], heights[i], floor, wall, world);
        }
        return rooms;
    }

    /**
     * Helper method that draws the generated rooms in the world.
     * @param rooms a non null array of rooms to be drawn on the world.
     */
    private void drawRooms(Room[] rooms) {
        for (Room room : rooms) {
            room.draw();
        }
    }

}

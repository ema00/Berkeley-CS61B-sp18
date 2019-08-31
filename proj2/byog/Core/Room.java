package byog.Core;

import byog.TileEngine.TETile;



/**
 * Room
 * Represents a square room in the game.
 * The room is completely determined by its position (x, y), size (width, height), tile type for the floor and the
 * walls, and by the world in which it is placed.
 * The walls representation is not included in this class.
 */
public class Room {

    /* Position of the room in the world, lower left corner. */
    private final int x;
    private final int y;
    /* Size of the room, in tiles. */
    private final int width;
    private final int height;
    /* Tile types for the floor and the walls. */
    private final TETile floor;
    private final TETile wall;
    /* World in which the room is to be drawn. */
    private final TETile[][] world;


    public Room(Point p, int width, int height, TETile floor, TETile wall, TETile[][] world) {
        this(p.x, p.y, width, height, floor, wall, world);
    }

    public Room(int x, int y, int width, int height, TETile floor, TETile wall, TETile[][] world) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.floor = floor;
        this.wall = wall;
        this.world = world;

        if (x < 0) {
            throw new RuntimeException("Room out of bounds, x: " + x + ".");
        }
        if (y < 0) {
            throw new RuntimeException("Room  out of bounds, y: " + y + ".");
        }
        if (world.length < x + width) {
            throw new RuntimeException("Room out of bounds, x: " + x + ", width: " + width + ".");
        }
        if (world[0].length < y + height) {
            throw new RuntimeException("Room out of bounds, y: " + y + ", height: " + height + ".");
        }
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public TETile floor() {
        return floor;
    }

    public TETile wall() {
        return wall;
    }

    public TETile[][] world() {
        return world;
    }

    /**
     * Takes two Room objects as parameters, and returns true if the body of both objects overlaps on the x direction.
     */
    static boolean overlapOnX(Room r1, Room r2) {
        return (r1.x <= r2.x && r2.x <= (r1.x + r1.width - 1)) || (r2.x <= r1.x && r1.x <= (r2.x + r2.width - 1));
    }

    /**
     * Takes two Room objects as parameters, and returns true if the body of both objects overlaps on the y direction.
     */
    static boolean overlapOnY(Room r1, Room r2) {
        return (r1.y <= r2.y && r2.y <= (r1.y + r1.height - 1)) || (r2.y <= r1.y && r1.y <= (r2.y + r2.height - 1));
    }

    /**
     * Draws this Room on the world which it references.
     */
    public void draw() {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                world[i][j] = floor;
            }
        }
    }

}
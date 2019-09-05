package byog.Core;

import java.util.ArrayList;
import java.util.List;
import byog.TileEngine.TETile;



/**
 * Room
 * Represents a square room in the game.
 * The room is completely determined by its position (x, y), size (width, height), tile type for
 * the floor and the walls, and by the world in which it is placed.
 * The walls representation is not included in this class.
 * @author Emanuel Aguirre
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


    public Room(Point p, int widthP, int heightP, TETile floorP, TETile wallP, TETile[][] worldP) {
        this(p.x, p.y, widthP, heightP, floorP, wallP, worldP);
    }

    public Room(int xp, int yp, int widthP, int heightP, TETile floorP, TETile wallP,
                TETile[][] worldP) {
        this.x = xp;
        this.y = yp;
        this.width = widthP;
        this.height = heightP;
        this.floor = floorP;
        this.wall = wallP;
        this.world = worldP;

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
            throw new RuntimeException("Room out of bounds, y: " + y + ", height: " + height
                    + ".");
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
     * Takes two Room objects as parameters, and returns true if the body of both objects overlaps
     * on the x direction.
     * @param r1 is one of the rooms to check for overlapping along the x direction.
     * @param r2 is one of the rooms to check for overlapping along the x direction.
     * @return true if both rooms have point with common coordinates along x.
     */
    static boolean overlapOnX(Room r1, Room r2) {
        return (r1.x <= r2.x && r2.x <= (r1.x + r1.width - 1))
                || (r2.x <= r1.x && r1.x <= (r2.x + r2.width - 1));
    }

    /**
     * Takes two Room objects as parameters, and returns true if the body of both objects overlaps
     * on the y direction.
     * @param r1 is one of the rooms to check for overlapping along the y direction.
     * @param r2 is one of the rooms to check for overlapping along the y direction.
     * @return true if both rooms have point with common coordinates along y.
     */
    static boolean overlapOnY(Room r1, Room r2) {
        return (r1.y <= r2.y && r2.y <= (r1.y + r1.height - 1))
                || (r2.y <= r1.y && r1.y <= (r2.y + r2.height - 1));
    }

    /**
     * Return the Room representation as a  List of Points, representing the set of coordinates
     * occupied by the room.
     * @return the set of points that the room occupies.
     */
    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                points.add(new Point(i, j));
            }
        }
        return points;
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

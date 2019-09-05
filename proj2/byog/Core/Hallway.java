package byog.Core;

import java.util.ArrayList;
import java.util.List;
import byog.TileEngine.TETile;



/**
 * Hallway
 * Represents a generic hallway. That is, a set of adjacent tiles in the world.
 * The walls representation is not included in this class.
 * @author Emanuel Aguirre
 */
public abstract class Hallway {

    /* Inner data types */

    /**
     * Segment
     * Represents a vertical(or horizontal) set of tiles forming a column (or row).
     */
    class Segment {
        private final Point p1;
        private final Point p2;
        private final Point[] points;

        Segment(Point pt1, Point pt2) {
            if (Point.alignedOnY(pt1, pt2)) {
                if (pt1.y < pt2.y) {
                    this.p1 = pt1;
                    this.p2 = pt2;
                } else {
                    this.p1 = pt2;
                    this.p2 = pt1;
                }
                int numPoints = this.p2.y - this.p1.y + 1;
                points = new Point[numPoints];
                for (int i = 0; i < numPoints; i++) {
                    points[i] = new Point(this.p1.x, this.p1.y + i);
                }
            } else if (Point.alignedOnX(pt1, pt2)) {
                if (pt1.x < pt2.x) {
                    this.p1 = pt1;
                    this.p2 = pt2;
                } else {
                    this.p1 = pt2;
                    this.p2 = pt1;
                }
                int numPoints = this.p2.x - this.p1.x + 1;
                points = new Point[numPoints];
                for (int i = 0; i < numPoints; i++) {
                    points[i] = new Point(this.p1.x + i, this.p1.y);
                }
            } else {
                throw new IllegalArgumentException("Trying to initialize "
                        + getClass() + " with points that are not aligned on x nor y.");
            }
        }
    }


    /* Class implementation */

    /* The set of segments that comprise the hallway. Must be initialized by subclasses.  */
    protected Segment[] segments;
    /* World in which the room is to be drawn. */
    protected TETile[][] world;
    /* Tile types for the floor and the walls. */
    protected TETile floor;
    protected TETile wall;


    /**
     * This function must be called within the constructor, to initialize the Hallway object.
     * @param worldP is the world on which the hallway is going to be drawn.
     * @param floorP is the type if TETile used for drawing the floor.
     * @param wallP is the type if TETile used for drawing the walls.
     */
    abstract void initialize(TETile[][] worldP, TETile floorP, TETile wallP);

    /**
     * Returns true if this instance is a straight hallway.
     */
    public boolean isStraight() {
        return segments.length == 1;
    }

    /**
     * Returns true if this Hallway contains the Point passed as parameter.
     * @param ps is the Point to be check if is in this hallway.
     * @return true if the point belongs to the hallway.
     */
    boolean contains(Point ps) {
        for (Segment segment : segments) {
            for (Point p : segment.points) {
                if (ps.equals(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the Room representation as a  List of Points, representing the set of coordinates occupied by the room.
     */
    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        for (Segment segment : segments) {
            for (Point p : segment.points) {
                points.add(p);
            }
        }
        return points;
    }

    /**
     * Draws this Hallway on the world which it references.
     */
    public void draw() {
        for (Segment segment : segments) {
            for (Point p : segment.points) {
                world[p.x][p.y] = floor;
            }
        }
    }

}

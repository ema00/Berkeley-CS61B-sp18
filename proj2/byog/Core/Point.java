package byog.Core;

/**
 * Represents a point coordinate in a plane, consisting of two coordinates: x and y.
 * The class is immutable.
 */
public class Point {

    public final int x;
    public final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns true if both Point instances are aligned on the x (horizontal) axis.
     */
    public static boolean alignedOnX(Point p1, Point p2) {
        return p1.y == p2.y;
    }

    /**
     * Returns true if both Point instances are aligned on the y (vertical) axis.
     */
    public static boolean alignedOnY(Point p1, Point p2) {
        return p1.x == p2.x;
    }

}

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

    /**
     * Equality operator.
     * @param o object on which to perform comparison.
     * @return true if objects are equal (deep equality).
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        Point other = (Point) o;
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Equality operator. Custom, not usable with java.util.Collections.
     * @return true if both Point instances are equal.
     */
    static boolean equals(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    /**
     * Equality operator. Custom, not usable with java.util.Collections.
     * @return true if this point is equal to the one passed as parameter.
     */
    boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }

}

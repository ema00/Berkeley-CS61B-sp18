package byog.Core;



/**
 * Represents a point coordinate in a plane, consisting of two coordinates: x and y.
 * The class is immutable.
 * @author Emanuel Aguirre
 */
public class Point {

    public final int x;
    public final int y;

    Point(int xp, int yp) {
        this.x = xp;
        this.y = yp;
    }

    /**
     * Returns true if both Point instances are aligned on the x (horizontal) axis.
     * @param p1 one of the points to compare for horizontal alignment.
     * @param p2 one of the points to compare for horizontal alignment.
     * @return true if both points are aligned on x.
     */
    public static boolean alignedOnX(Point p1, Point p2) {
        return p1.y == p2.y;
    }

    /**
     * Returns true if both Point instances are aligned on the y (vertical) axis.
     * @param p1 one of the points to compare for vertical alignment.
     * @param p2 one of the points to compare for vertical alignment.
     * @return true if both points are aligned on y.
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
     * @param p1 one of the points to compare for equality.
     * @param p2 one of the points to compare for equality.
     * @return true if both Point instances are equal.
     */
    static boolean equals(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }

    /**
     * Equality operator. Custom, not usable with java.util.Collections.
     * @param other is the object on which to compare this for equality.
     * @return true if this point is equal to the one passed as parameter.
     */
    boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }

}

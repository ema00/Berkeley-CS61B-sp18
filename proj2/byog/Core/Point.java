package byog.Core;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import java.io.Serializable;



/**
 * Represents a point coordinate in a plane, consisting of two coordinates: x and y.
 * The class is immutable.
 * @author Emanuel Aguirre
 */
public class Point implements Serializable {

    private static final long serialVersionUID = 123123123123126L;

    private final int x;
    private final int y;


    Point(int xp, int yp) {
        this.x = xp;
        this.y = yp;
    }

    /**
     * Returns the x coordinate of the point (x, y).
     * @return the value of the x coordinate of the point (x, y).
     */
    public int x() {
        return x;
    }

    /**
     * Returns the y coordinate of the point (x, y).
     * @return the value of the y coordinate of the point (x, y).
     */
    public int y() {
        return y;
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
     * Returns the calculated hash code for an object of type Point.
     * @return the calculated hash code for an object of type Point.
     */
    @Override
    public int hashCode() {
        return x + y;
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

}

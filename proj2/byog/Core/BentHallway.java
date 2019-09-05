package byog.Core;

import byog.TileEngine.TETile;



/**
 * BentHallway
 * Represents a hallway composed of a horizontal and a vertical segment.
 * @author Emanuel Aguirre
 */
public class BentHallway extends Hallway {

    /* These 3 points define completely a bent hallway formed by 2 straight segments. */
    private final Point end1;
    private final Point corner;
    private final Point end2;

    /**
     * BentHallway constructor. The hallway is constructed by 3 points, one of which represents the
     * corner.
     * @param p1 The initial (or final) point of the hallway.
     * @param p2 The corner of the hallway. This must be the corner, or construction will fail with
     *           an exception.
     * @param p3 The final (or initial) point of the hallway.
     * @param world is the world on which the hallway is to be drawn.
     * @param floor is the type if TETile used for drawing the floor.
     * @param wall is the type if TETile used for drawing the walls.
     */
    public BentHallway(Point p1, Point p2, Point p3, TETile[][] world, TETile floor, TETile wall) {
        if (p1 == null || p2 == null || p3 == null
                || world == null || floor == null || wall == null) {
            throw new IllegalArgumentException("Trying to initialize "
                    + getClass() + " with null argument(s).");
        }
        if (!(Point.alignedOnX(p1, p2) && Point.alignedOnY(p2, p3))
                && !(Point.alignedOnY(p1, p2) && Point.alignedOnX(p2, p3))) {
            throw new IllegalArgumentException("Trying to initialize "
                    + getClass() + " with points that don't form two orthogonal segments.");
        }
        this.end1 = p1;
        this.corner = p2;
        this.end2 = p3;
        segments = new Segment[2];
        segments[0] = new Segment(this.end1, this.corner);
        segments[1] = new Segment(this.corner, this.end2);
        initialize(world, floor, wall);
    }

    @Override
    void initialize(TETile[][] world, TETile floor, TETile wall) {
        this.world = world;
        this.floor = floor;
        this.wall = wall;
    }

}

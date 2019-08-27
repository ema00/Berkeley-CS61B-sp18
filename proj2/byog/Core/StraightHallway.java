package byog.Core;

import byog.TileEngine.TETile;



/**
 * StraightHallway
 * Represents a straight hallway, either vertical or horizontal.
 */
public class StraightHallway extends Hallway {

    private final Point p1;
    private final Point p2;

    public StraightHallway(Point p1, Point p2, TETile[][] world, TETile floor, TETile wall) {
        if (p1 == null || p2 == null || world == null || floor == null || wall == null) {
            throw new IllegalArgumentException("Trying to initialize "
                    + getClass() + " with null argument(s).");
        }
        this.p1 = p1;
        this.p2 = p2;
        segments = new Segment[1];
        segments[0] = new Segment(this.p1, this.p2);
        initialize(world, floor, wall);
    }

    @Override
    void initialize(TETile[][] world, TETile floor, TETile wall) {
        this.world = world;
        this.floor = floor;
        this.wall = wall;
    }

}
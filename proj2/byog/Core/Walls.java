package byog.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import byog.TileEngine.TETile;



/**
 * Walls
 * Represents a set of walls in the game, that are to be drawn as the limit of rooms and hallways.
 * When creating a Walls object, the type of TETile for the floor is needed in order for the walls
 * to be able to be generated.
 * Walls will only be created around a set of points of the floor type.
 * IMPORTANT: Lists are used internally instead o the more appropriate Set data structure because
 * Sets are no been yet seen in the program at the moment of project 2.
 */
public class Walls {

    /* points List represents where each wall is placed. */
    private final List<Point> points;
    /* gPoints (geometry points) List represents where points for rooms and walls are placed. */
    private final List<Point> gPoints;
    private final List<Room> rooms;
    private final List<Hallway> hallways;
    private final TETile[][] world;
    /* TETile that represent the floor for rooms and hallways in the world. */
    private final TETile floor;
    /* TETile that represent the wall for rooms and hallways in the world. */
    private final TETile wall;


    public Walls(Room[] rooms, Hallway[] hallways, TETile floor, TETile wall, TETile[][] world) {
        this.rooms = Arrays.asList(rooms);
        this.hallways = Arrays.asList(hallways);
        this.floor = floor;
        this.wall = wall;
        this.world = world;
        this.points = new ArrayList<>();
        this.gPoints = new ArrayList<>();
        populateGeometryPoints();
        generateWalls();
    }

    public Walls(List<Room> rooms, List<Hallway> hallways, TETile floor, TETile wall, TETile[][] world) {
        this.rooms = rooms;
        this.hallways = hallways;
        this.floor = floor;
        this.wall = wall;
        this.world = world;
        this.points = new ArrayList<>();
        this.gPoints = new ArrayList<>();
        populateGeometryPoints();
        generateWalls();
    }

    public Walls(List<Point> gPoints, TETile floor, TETile wall, TETile[][] world) {
        this.rooms = null;
        this.hallways = null;
        this.floor = floor;
        this.wall = wall;
        this.world = world;
        this.points = new ArrayList<>();
        this.gPoints = gPoints;
        generateWalls();
    }

    /**
     * Populates List<Point> gPoints, which contains all the points corresponding to rooms and walls that
     * are in the world.
     */
    private void populateGeometryPoints() {
        for (Room room : rooms) {
            List<Point> roomPoints = room.getPoints();
            for (Point p : roomPoints) {
                if (!gPoints.contains(p)) {
                    gPoints.add(p);
                }
            }
        }
        for (Hallway hallway : hallways) {
            List<Point> hallwayPoints = hallway.getPoints();
            for (Point p : hallwayPoints) {
                if (!gPoints.contains(p)) {
                    gPoints.add(p);
                }
            }
        }
    }

    /**
     * Generates all the walls for the world.
     * First, sweep the world left-right and bottom-up, to add right and upper walls, and top-right corners.
     * Second, sweep the world right-left and top-down, to add left and lower walls, and bottom-left corners.
     * Third, sweep the world left-right and bottom-up, to add top-left and bottom-right corners.
     */
    private void generateWalls() {
        // 1-sweep left-right and bottom-up, draw right and upper walls, and top-right corners
        for (int x = 1; x < world.length; x++) {
            for (int y = 1; y < world[x].length; y++) {
                Point current = new Point(x, y);
                Point below = new Point(x, y - 1);
                Point left = new Point(x - 1, y);
                Point belowLeft = new Point(x - 1, y - 1);
                if (gPoints.contains(current)) {
                    continue;
                }
                if (gPoints.contains(left)) {
                    addWall(x, y);
                }
                if (gPoints.contains(below)) {
                    addWall(x, y);
                }
                if (points.contains(left) && points.contains(below) && gPoints.contains(belowLeft)) {
                    addWall(x, y);
                }
            }
        }
        // 2-sweep right-left and top-down, draw left and lower walls, and bottom-left corners
        for (int x = world.length - 2; x >= 0 ; x--) {
            for (int y = world[x].length - 2; y >= 0 ; y--) {
                Point current = new Point(x, y);
                Point above = new Point(x, y + 1);
                Point right = new Point(x + 1, y);
                Point aboveRight = new Point(x + 1, y + 1);
                if (gPoints.contains(current) || points.contains(current)) {
                    continue;
                }
                if (gPoints.contains(right)) {
                    addWall(x, y);
                }
                if (gPoints.contains(above)) {
                    addWall(x, y);
                }
                if (points.contains(right) && points.contains(above) && gPoints.contains(aboveRight)) {
                    addWall(x, y);
                }
            }
        }
        // 3-sweep left-right and bottom-up, draw top-left and bottom-right corners
        for (int x = 1; x < world.length; x++) {
            for (int y = 1; y < world[x].length; y++) {
                Point current = new Point(x, y);
                Point below = new Point(x, y - 1);
                Point left = new Point(x - 1, y);
                Point belowLeft = new Point(x - 1, y - 1);
                if (!points.contains(current)) {
                    continue;
                }
                if (gPoints.contains(below) && !gPoints.contains(left) && points.contains(belowLeft)) {
                    addWall(x - 1, y);
                }
                if (!gPoints.contains(below) && gPoints.contains(left) && points.contains(belowLeft)) {
                    addWall(x, y - 1);
                }
            }
        }
    }

    /**
     * Adds a wall, on the world, at the coordinates passed as parameter.
     */
    private void addWall(int x, int y) {
        Point p = new Point(x, y);
        points.add(p);
    }

    /**
     * Adds a wall, on the world, at the point passed as parameter.
     */
    private void addWall(Point p) {
        points.add(p);
    }

    /**
     * Draws the Walls on the world which they reference.
     */
    public void draw() {
        for (Point p : points) {
            world[p.x][p.y] = wall;
        }
    }

}

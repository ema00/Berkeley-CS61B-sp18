package byog.Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import byog.TileEngine.TETile;
import static byog.Core.Room.overlapOnX;
import static byog.Core.Room.overlapOnY;



/**
 * RandomWorldGenerator
 * Class to generate random world consisting of rooms, hallways, and walls (that set boundaries).
 * Can generate: Rooms, Hallways (that connect Rooms), and Walls.
 * The order in which the objects must be generated is: Rooms, Hallways, Walls.
 * Usage:
 *      RandomWorldGenerator rwg = new RandomWorldGenerator(world, Tileset.FLOOR, Tileset.WALL,
 *          new Random());
 *      Room[] rooms = rwg.generateRooms(5, 6, 1, 13);
 *      Hallway[] hallways = rwg.generateHallways(rooms);
 *      Walls walls = rwg.generateWalls(rooms, hallways);
 * @author Emanuel Aguirre
 */
public class RandomWorldGenerator {

    /* The width of the walls in number of tiles. */
    private static final int WALL_SIZE = 1;

    /* World on which the room is to be generated. */
    private TETile[][] world;
    /* Tile types for the floor and the walls. */
    private TETile floor;
    private TETile wall;
    /* Random number generator from java.util */
    private Random random;


    public RandomWorldGenerator(TETile[][] worldP, TETile floorP, TETile wallP, Random randomP) {
        this.world = worldP;
        this.floor = floorP;
        this.wall = wallP;
        this.random = randomP;
    }

    /**
     * Generates random rooms in the world.
     * No collision detection between rooms, which means that room floors can be overlapping.
     * @param sideMin is the minimum dimension for the width or height of a room.
     * @param sideMax is the maximum dimension for the width or height of a room.
     * @param deltaWH is the maximum allowable difference between the width and height of any room.
     * @param n the number of rooms to draw.
     * @return a List of rooms that might overlap.
     */
    public List<Room> generateRooms(int sideMin, int sideMax, int deltaWH, int n) {
        if (sideMax + deltaWH + 2 * WALL_SIZE >= world.length
                || sideMax + deltaWH + 2 * WALL_SIZE >= world[0].length) {
            throw new RuntimeException("Room size is too big for world size.");
        }

        int[] widths = randomUniformArray(sideMin, sideMax + 1, n);
        int[] heights = randomUniformArray(sideMin - deltaWH, sideMax + deltaWH + 1, n);
        int xMin = WALL_SIZE;
        int yMin = WALL_SIZE;
        int xMax = world.length - (sideMax + deltaWH + WALL_SIZE);
        int yMax = world[0].length - (sideMax + deltaWH + WALL_SIZE);
        List<Room> rooms = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            int x = RandomUtils.uniform(random, xMin, xMax);
            int y = RandomUtils.uniform(random, yMin, yMax);
            rooms.add(new Room(new Point(x, y), widths[i], heights[i], floor, wall, world));
        }
        return rooms;
    }

    /**
     * Generates random rooms, that don't overlap, in the world.
     * Collision detection between rooms, which means that room floors won't overlap.
     * Every room will be tried to be added a fixed number of times, if fails, won't be added.
     * As some rooms may not be added, the total number of rooms might be lower than n.
     * @param sideMin is the minimum dimension for the width or height of a room.
     * @param sideMax is the maximum dimension for the width or height of a room.
     * @param deltaWH is the maximum allowable difference between the width and height of any room.
     * @param n the maximum number of rooms to draw (actual number may be lower due to collisions).
     * @param numTries is the number of times that each room will be tried to be added.
     * @return a List of rooms that don't overlap.
     */
    public List<Room> generateRoomsNoOverlap(
            int sideMin, int sideMax, int deltaWH, int n, int numTries) {

        if (sideMax + deltaWH + 2 * WALL_SIZE >= world.length
                || sideMax + deltaWH + 2 * WALL_SIZE >= world[0].length) {
            throw new RuntimeException("Room size is too big for world size.");
        }

        int[] widths = randomUniformArray(sideMin, sideMax + 1, n);
        int[] heights = randomUniformArray(sideMin - deltaWH, sideMax + deltaWH + 1, n);
        int xMin = WALL_SIZE;
        int yMin = WALL_SIZE;
        int xMax = world.length - (sideMax + deltaWH + WALL_SIZE);
        int yMax = world[0].length - (sideMax + deltaWH + WALL_SIZE);
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = RandomUtils.uniform(random, xMin, xMax);
            int y = RandomUtils.uniform(random, yMin, yMax);
            for (int j = 0; j < numTries; j++) {
                Room room = new Room(new Point(x, y), widths[i], heights[i], floor, wall, world);
                List<Room> overlapping = rooms.stream().filter(
                    (r) -> overlapOnX(r, room) && overlapOnY(r, room)).collect(Collectors.toList());
                if (overlapping.isEmpty()) {
                    rooms.add(room);
                    break;
                }
            }
        }
        return rooms;
    }

    /**
     * Generates random straight and bent hallways to connect the rooms passed as parameter.
     * First shuffle rooms passed as parameter, to add randomness in connections.
     * Then connects rooms one by one. If n rooms, makes n - 1 connections.
     * @param rooms the Room instances to connect among.
     * @return an array of hallways connecting the rooms passed as parameter.
     */
    public List<Hallway> generateHallways(List<Room> rooms) {
        List<Hallway> hallways = new ArrayList<>();
        List<Room> connected = new ArrayList<>();

        Room[] roomsShuffled = new Room[rooms.size()];
        roomsShuffled = rooms.toArray(roomsShuffled);
        RandomUtils.shuffle(random, roomsShuffled);
        rooms = Arrays.asList(roomsShuffled);

        connected.add(rooms.get(0));
        for (int i = 1; i < rooms.size(); i++) {
            Room randomRoom = rooms.get(i);
            if (overlapOnX(randomRoom, connected.get(i - 1))) {
                hallways.add(connectAlongY(randomRoom, connected.get(i - 1)));
            } else if (overlapOnY(randomRoom, connected.get(i - 1))) {
                hallways.add(connectAlongX(randomRoom, connected.get(i - 1)));
            } else if (RandomUtils.bernoulli(random)) {
                hallways.add(connectRight(randomRoom, connected.get(i - 1)));
            } else {
                hallways.add(connectLeft(randomRoom, connected.get(i - 1)));
            }
            connected.add(i, randomRoom);
        }
        return hallways;
    }

    /**
     * Generates all the walls as the boundaries of the rooms and hallways passed as parameters.
     * @param rooms are the rooms around which to place the walls.
     * @param hallways ere the hallways around which to place the walls.
     * @return a Walls object to be drawn in the world.
     */
    public Walls generateWalls(List<Room> rooms, List<Hallway> hallways) {
        return new Walls(rooms, hallways, floor, wall, world);
    }

    /**
     * Generates all the walls as the boundaries of the coordinates passed as parameters.
     * @param coordinates the points that comprise the geometry around which to draw the walls.
     * @return a Walls object to be drawn in the world.
     */
    public Walls generateWalls(List<Point> coordinates) {
        return new Walls(coordinates, floor, wall, world);
    }

    /**
     * Returns a List of all the coordinates occupied by rooms and hallways passed as parameters.
     * This method is helpful to get all the points on which a player can move.
     * @param rooms a List of rooms.
     * @param hallways a List of hallways.
     * @return a List of all the coordinates (Point objects) that the parameters occupy.
     */
    public List<Point> getCoordinates(List<Room> rooms, List<Hallway> hallways) {
        List<Point> coordinates = new ArrayList<>();
        for (Room room : rooms) {
            room.getPoints().forEach(point -> {
                if (!coordinates.contains(point)) {
                    coordinates.add(point);
                }
            });
        }
        for (Hallway hallway : hallways) {
            hallway.getPoints().forEach(point -> {
                if (!coordinates.contains(point)) {
                    coordinates.add(point);
                }
            });
        }
        return coordinates;
    }

    /**
     * Connects two rooms with a bent hallway, starting from the leftmost to the rightmost.
     * The hallway is generated randomly.
     * @param room1 is one of the rooms to connect with a hallway.
     * @param room2 is one of the rooms to connect with a hallway.
     * @return a hallway connecting both rooms passed as parameters.
     */
    private Hallway connectLeft(Room room1, Room room2) {
        Room left;
        Room right;
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int x1 = right.x() - 1;
        int x2 = RandomUtils.uniform(random, left.x(), left.x() + left.width());
        int y2 = RandomUtils.uniform(random, right.y(), right.y() + right.height());
        int y3 = right.y() < left.y() ? (left.y() - 1) : (left.y() + left.height());
        Point p1 = new Point(x1, y2);
        Point p2 = new Point(x2, y2);
        Point p3 = new Point(x2, y3);
        return new BentHallway(p1, p2, p3, world, floor, wall);
    }

    /**
     * Connects two rooms with a bent hallway, starting from the rightmost to the leftmost.
     * The hallway is generated randomly.
     * @param room1 is one of the rooms to connect with a hallway.
     * @param room2 is one of the rooms to connect with a hallway.
     * @return a hallway connecting both rooms passed as parameters.
     */
    private Hallway connectRight(Room room1, Room room2) {
        Room left;
        Room right;
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int x1 = left.x() + left.width();
        int x2 = RandomUtils.uniform(random, right.x(), right.x() + right.width());
        int y2 = RandomUtils.uniform(random, left.y(), left.y() + left.height());
        int y3 = left.y() < right.y() ? (right.y() - 1) : (right.y() + right.height());
        Point p1 = new Point(x1, y2);
        Point p2 = new Point(x2, y2);
        Point p3 = new Point(x2, y3);
        return new BentHallway(p1, p2, p3, world, floor, wall);
    }

    /**
     * Connects two rooms that overlap on the y axis. A horizontal hallway.
     * The hallway is generated randomly.
     * @param room1 is one of the rooms to connect with a hallway.
     * @param room2 is one of the rooms to connect with a hallway.
     * @return a hallway connecting both rooms passed as parameters.
     */
    private Hallway connectAlongX(Room room1, Room room2) {
        Room high;
        Room low;
        Room left;
        Room right;
        if (room1.y() < room2.y()) {
            high = room2;
            low = room1;
        } else {
            high = room1;
            low = room2;
        }
        if (room1.x() + room1.width() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int y = RandomUtils.uniform(random, high.y(), low.y() + low.height());
        return new StraightHallway(new Point(left.x() + left.width(), y),
                new Point(right.x() - 1, y), world, floor, wall);
    }

    /**
     * Connects two rooms that overlap on the x axis. A vertical hallway.
     * The hallway is generated randomly.
     * @param room1 is one of the rooms to connect with a hallway.
     * @param room2 is one of the rooms to connect with a hallway.
     * @return a hallway connecting both rooms passed as parameters.
     */
    private Hallway connectAlongY(Room room1, Room room2) {
        Room high;
        Room low;
        Room left;
        Room right;
        if (room1.y() + room1.height() < room2.y()) {
            high = room2;
            low = room1;
        } else {
            high = room1;
            low = room2;
        }
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        } else {
            left = room2;
            right = room1;
        }
        int x = RandomUtils.uniform(random, right.x(), left.x() + left.width());
        return new StraightHallway(new Point(x, low.y() + low.height()),
                new Point(x, high.y() - 1), world, floor, wall);
    }

    /**
     * Returns an Array of n random integers uniformly distributed in [min, max).
     * @param min the minimum value that any number in the array will take (closed interval).
     * @param max the minimum value that any number in the array will take (open interval).
     * @param n the number of integers in the resulting array.
     * @return an Array of int, with random values uniformly distributed in [min, max).
     */
    private int[] randomUniformArray(int min, int max, int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = RandomUtils.uniform(random, min, max);
        }
        return result;
    }

}

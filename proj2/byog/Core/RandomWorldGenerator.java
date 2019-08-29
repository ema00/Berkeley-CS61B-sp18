package byog.Core;

import java.util.Arrays;
import java.util.Random;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


public class RandomWorldGenerator {

    private TETile[][] world;
    private TETile floor;
    private TETile wall;
    private Random random;


    public RandomWorldGenerator(TETile[][] world, TETile floor, TETile wall, Random random) {
        this.world = world;
        this.floor = floor;
        this.wall = wall;
        this.random = random;
    }

    /**
     * Generates random rooms in the world.
     * No collision detection between rooms, which means that room floors can be overlapping.
     * @param sideMin is the minimum dimension for the width or height of a room.
     * @param sideMax is the maximum dimension for the width or height of a room.
     * @param deltaWH is the maximum allowable difference between the width and height of any room.
     * @param n the number of rooms to draw.
     */
    public Room[] generateRooms(int sideMin, int sideMax, int deltaWH, int n) {
        final int WALL_SIZE = 1;

        if (sideMax + deltaWH + 2 * WALL_SIZE >= world.length || sideMax + deltaWH + 2 * WALL_SIZE >= world[0].length) {
            throw new RuntimeException("Room size is too big for world size.");
        }

        int[] widths = new int[n];
        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            widths[i] = RandomUtils.uniform(random, sideMin, sideMax + 1);
        }
        for (int i = 0; i < n; i++) {
            heights[i] = widths[i] + RandomUtils.uniform(random, -deltaWH, deltaWH + 1);
        }

        int xMin = WALL_SIZE;
        int yMin = WALL_SIZE;
        int xMax = world.length - (sideMax + deltaWH + WALL_SIZE);
        int yMax = world[0].length - (sideMax + deltaWH + WALL_SIZE);
        Room[] rooms = new Room[n];
        for (int i = n - 1; i >= 0; i--) {
            int x = RandomUtils.uniform(random, xMin, xMax);
            int y = RandomUtils.uniform(random, yMin, yMax);
            rooms[i] = new Room(new Point(x, y), widths[i], heights[i], floor, wall, world);
        }
        return rooms;
    }

    /**
     * Generates random straight and bent hallways to connect the rooms passed as parameter.
     * @param rooms the Room instances to connect among.
     * @return an array of hallways connecting the rooms passed as parameter.
     */
    public Hallway[] generateHallways(Room[] rooms) {
        Hallway[] hallways = new Hallway[rooms.length - 1];
        RandomUtils.shuffle(random, rooms);
        Room[] connected = new Room[rooms.length];

        connected[0] = rooms[0];
        for (int i = 1; i < rooms.length; i++) {
            Room randomRoom = rooms[RandomUtils.uniform(random, 0, rooms.length)];
            while (Arrays.asList(connected).contains(randomRoom)) {
                randomRoom = rooms[RandomUtils.uniform(random, 0, rooms.length)];
            }
            connected[i] = randomRoom;
            if (Room.overlapOnX(randomRoom, connected[i - 1])) {
                hallways[i - 1] = connectAlongY(randomRoom, connected[i - 1]);
            }
            else if (Room.overlapOnY(randomRoom, connected[i - 1])) {
                hallways[i - 1] = connectAlongX(randomRoom, connected[i - 1]);
            }
            else if (RandomUtils.bernoulli(random)) {
                hallways[i - 1] = connectRight(randomRoom, connected[i - 1]);
            }
            else {
                hallways[i - 1] = connectLeft(randomRoom, connected[i - 1]);
            }
        }
        return hallways;
    }

    /**
     * Connects two rooms with a bent hallway, starting from the leftmost room to the rightmost room.
     * The hallway is generated randomly.
     * @return a hallway connecting both rooms passed as parameters.
     */
    private Hallway connectLeft(Room room1, Room room2){
        Room left;
        Room right;
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        }
        else {
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
     * Connects two rooms with a bent hallway, starting from the rightmost room to the leftmost room.
     * The hallway is generated randomly.
     * @return a hallway connecting both rooms passed as parameters.
     */
    private Hallway connectRight(Room room1, Room room2) {
        Room left;
        Room right;
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        }
        else {
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
     * Connects two rooms that overlap on the y axis. A horizontal hallway. The hallway is generated randomly.
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
        }
        else {
            high = room1;
            low = room2;
        }
        if (room1.x() + room1.width() < room2.x()) {
            left = room1;
            right = room2;
        }
        else {
            left = room2;
            right = room1;
        }
        int y = RandomUtils.uniform(random, high.y(), low.y() + low.height());
        return new StraightHallway(new Point(left.x() + left.width(), y), new Point(right.x() - 1, y),
                world, floor, wall);
    }

    /**
     * Connects two rooms that overlap on the x axis. A vertical hallway. The hallway is generated randomly.
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
        }
        else {
            high = room1;
            low = room2;
        }
        if (room1.x() < room2.x()) {
            left = room1;
            right = room2;
        }
        else {
            left = room2;
            right = room1;
        }
        int x = RandomUtils.uniform(random, right.x(), left.x() + left.width());
        return new StraightHallway(new Point(x, low.y() + low.height()), new Point(x, high.y() - 1),
                world, floor, wall);
    }

    public void drawWalls() {
        // sweep left-right and bottom-up, draw right and upper walls, and top-right corners
        for (int x = 1; x < world.length; x++) {
            for (int y = 1; y < world[x].length; y++) {
                if (world[x - 1][y] == floor && world[x][y] != floor) {
                    world[x][y] = wall;
                }
                if (world[x][y - 1] == floor && world[x][y] != floor) {
                    world[x][y] = wall;
                }
                if (world[x - 1][y] == wall && world[x][y - 1] == wall &&
                        world[x - 1][y - 1] == floor && world[x][y] != floor) {
                    world[x][y] = wall;
                }
            }
        }
        // sweep right-left and top-down, draw left and lower walls, and bottom-left corners
        for (int x = world.length - 2; x >= 0 ; x--) {
            for (int y = world[x].length - 2; y >= 0 ; y--) {
                if (world[x + 1][y] == floor && world[x][y] != floor) {
                    world[x][y] = wall;
                }
                if (world[x][y + 1] == floor && world[x][y] != floor) {
                    world[x][y] = wall;
                }
                if (world[x + 1][y] == wall && world[x][y + 1] == wall &&
                        world[x + 1][y + 1] == floor && world[x][y] != floor) {
                    world[x][y] = wall;
                }
            }
        }
        // sweep left-right and bottom-up, draw top-left and bottom-right corners
        for (int x = 1; x < world.length; x++) {
            for (int y = 1; y < world[x].length; y++) {
                if (world[x][y] == wall && world[x][y - 1] == floor && world[x - 1][y] != floor && world[x - 1][y - 1] == wall) {
                    world[x - 1][y] = wall;
                }
                if (world[x][y] == wall && world[x][y - 1] != floor && world[x - 1][y] == floor && world[x - 1][y - 1] == wall) {
                    world[x][y - 1] = wall;
                }
            }
        }
    }
}

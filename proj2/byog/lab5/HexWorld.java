package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 * The hexagons are arranged in 5 columns of: 3, 4, 5, 4 and 3 hexagons each.
 */
public class HexWorld {

    /* The following are necessary to determine the width and height of the world */
    /* Size of the hexagons to be drawn */
    private static final int HEXAGON_SIZE = 3;
    /* Maximum number of hexagons in the vertical direction */
    private static final int MAX_NUM_VERTICAL_HEXES = 5;

    /* This is for selecting the tiles randomly */
    private static final long SEED = 2873122;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {

        /* Calculate height of the world */
        final int HEIGHT = MAX_NUM_VERTICAL_HEXES * calculateHeight(HEXAGON_SIZE);

        /* Calculate the positions of the hexagons, for 5 columns of hexagons */
        Point p0 = new Point(0, calculateHeight(HEXAGON_SIZE));
        Point p1 = bottomRightNeighborPosition(p0, HEXAGON_SIZE);
        Point p2 = bottomRightNeighborPosition(p1, HEXAGON_SIZE);
        Point p3 = topRightNeighborPosition(p2, HEXAGON_SIZE);
        Point p4 = topRightNeighborPosition(p3, HEXAGON_SIZE);

        /* Calculate width of the world, for 5 columns of hexagons */
        Point p5 = bottomRightNeighborPosition(p4, HEXAGON_SIZE);
        final int WIDTH = p5.x + HEXAGON_SIZE;

        // Initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // Initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // Draw columns of hexagons
        drawRandomVerticalHexes(p0, 3, HEXAGON_SIZE, world);
        drawRandomVerticalHexes(p1, 4, HEXAGON_SIZE, world);
        drawRandomVerticalHexes(p2, 5, HEXAGON_SIZE, world);
        drawRandomVerticalHexes(p3, 4, HEXAGON_SIZE, world);
        drawRandomVerticalHexes(p4, 3, HEXAGON_SIZE, world);

        // draws the world to the screen
        ter.renderFrame(world);
    }

    /**
     * Draws a column of hexagons, made by random tiles.
     * @param p are the coordinates of the bottom hexagon.
     * @param n is the number of hexagons that the column contains.
     * @param size is the size of the hexagons to be drawn.
     * @param world is the world where the hexagons are to be drawn.
     */
    private static void drawRandomVerticalHexes(Point p, int n, int size, TETile[][] world) {
        int height = calculateHeight(size);
        for (int i = 0; i < n; i++) {
            int x = p.x;
            int y = p.y + i * height;
            TETile tile = randomTile();
            addHexagon(x, y, size, tile, world);
        }
    }


    /**
     * Generates an hexagonal region, comprised of tiles.
     * The hexagon is generated by drawing the columns that make it, one by one.
     * The sides of the hexagon are the lower, upper, lower left and right, and upper left and right.
     * Example of an hexagon of size 3 (+ is the (x, y) coordinate of the hexagon):
     *     ***
     *    *****
     *   *******
     *   *******
     *    *****
     *   + ***
     * @param x coordinate of the lower left corner of the hexagon.
     * @param y coordinate of the lower left corner of the hexagon.
     * @param size number of tiles per side of the hexagon.
     * @param tile is the type of tile used to generate the hexagon.
     */
    private static void addHexagon(int x, int y, int size, TETile tile, TETile[][] world) {
        int height = calculateHeight(size);
        int[] numTilesPerColumn = calculateNumberTilesPerColumn(size);
        int numCols = numTilesPerColumn.length;
        for (int c = 0; c < numCols; c++) {
            int numTilesInCol = numTilesPerColumn[c];
            int yOffset = (height - numTilesInCol) / 2;
            addColumn(world, x, y, c, yOffset, numTilesInCol, tile);
        }
    }

    /**
     * Calculates the number of tiles to be drawn at each column for drawing an hexagon.
     * @param size is the size of the sides of the hexagon.
     * @return an array containing the number of tiles to be drawn in each column.
     */
    private static int[] calculateNumberTilesPerColumn(int size) {
        int numCols = (size - 1) + size + (size - 1);
        int[] numTilesPerColumn = new int[numCols];
        int height = calculateHeight(size);
        for (int i = 0; i < numCols; i++) {
            if (i < size - 1) { numTilesPerColumn[i] = 2 * (i + 1); }
            else if (i >= 2 * (size - 1)) { numTilesPerColumn[i] = 2 * (numCols - i); }
            else { numTilesPerColumn[i] = height; }
        }
        return numTilesPerColumn;
    }

    /**
     * Calculates the total height, in tiles, of an hexagon, based on the size (in tiles) of its sides.
     * @param size is the size of the sides of the hexagon.
     * @return the total height of an hexagon, in tiles.
     */
    private static int calculateHeight(int size) {
        return 2 * size;
    }

    /**
     * Draws a column of tiles, at the offset of the y position of the given column.
     * @param world is the world on which the column is to be drawn.
     * @param x is the base y position from where to start drawing the tiles.
     * @param y is the base y position from where to start drawing the tiles.
     * @param xOffset is the offset from x, from where the drawing starts.
     * @param yOffset is the offset from y, from where the drawing starts.
     * @param numTiles is the number of tiles to be drawn.
     * @param tile is the type of tile to be drawn.
     */
    private static void addColumn(TETile[][] world, int x, int y, int xOffset, int yOffset, int numTiles, TETile tile) {
        int start = y + yOffset;
        int end = start + numTiles;
        for (int r = start; r < end; r++) {
            world[x + xOffset][r] = tile;
        }
    }

    /**
     * Computes the position of the coordinate of the origin of the hexagon that could be located
     * to the top right of an hexagon (whose origin is p), in a way that both are adjacent.
     * Both hexagons must be of the same size.
     * @param p is the origin of the hexagon taken as reference.
     * @param size is the size of the hexagons, in tiles.
     * @return the position of the origin of the hexagon adjacent to the top right.
     */
    private static Point topRightNeighborPosition(Point p, int size) {
        int x = p.x + size + (size - 1);
        int y = p.y + size;
        return new Point(x, y);
    }

    /**
     * Computes the position of the coordinate of the origin of the hexagon that could be located
     * to the bottom right of an hexagon (whose origin is p), in a way that both are adjacent.
     * Both hexagons must be of the same size.
     * @param p is the origin of the hexagon taken as reference.
     * @param size is the size of the hexagons, in tiles.
     * @return the position of the origin of the hexagon adjacent to the bottom right.
     */
    private static Point bottomRightNeighborPosition(Point p, int size) {
        int x = p.x + size + (size - 1);
        int y = p.y - size;
        return new Point(x, y);
    }

    /**
     * Picks a RANDOM tile with equal chances between a subset of the available tile types.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.FLOWER;
            case 1: return Tileset.TREE;
            case 2: return Tileset.WALL;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            case 5: return Tileset.WATER;
            case 6: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

}

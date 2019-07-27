package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
        for (int x = 20; x < 35; x += 1) {
            for (int y = 5; y < 10; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }

        addHexagon(20, 20, 3, Tileset.FLOWER, world);
        addHexagon(40, 10, 2, Tileset.MOUNTAIN, world);
        addHexagon(50, 5, 4, Tileset.TREE, world);
        addColumn(world[0], 10, 0, 5, Tileset.FLOWER);

        // draws the world to the screen
        ter.renderFrame(world);
    }


    /**
     * Generates an hexagonal region, comprised of tiles.
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
            int offset = (height - numTilesInCol) / 2;
            TETile[] col = world[x + c];
            addColumn(col, y, offset, numTilesInCol, tile);
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
     * @param col is the column of the world on which to draw the tiles.
     * @param y is the base y position from where to start drawing the tiles.
     * @param offset is the offset from y, from where the drawing starts.
     * @param numTiles is the number of tiles to be drawn.
     * @param tile is the type of tile to be drawn.
     */
    private static void addColumn(TETile[] col, int y, int offset, int numTiles, TETile tile) {
        int start = y + offset;
        int end = start + numTiles;
        for (int r = start; r < end; r++) {
            col[r] = tile;
        }
    }

}

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
        int numCols = (size - 1) + size + (size - 1);
        int maxHeight = 2 * size;
        int numTiles = 2;
        for (int c = 0; c < numCols; c++) {
            TETile[] col = world[x + c];
            int offset = (maxHeight - numTiles) / 2;
            addColumn(col, y, offset, numTiles, tile);
            if (c < size - 1) { numTiles += 2; }
            if (c >= 2 * (size - 1)) { numTiles -= 2; }
        }
    }

    /**
     *
     * @param col
     * @param y
     * @param offset
     * @param numTiles
     * @param tile
     */
    private static void addColumn(TETile[] col, int y, int offset, int numTiles, TETile tile) {
        int start = y + offset;
        int end = start + numTiles;
        for (int r = start; r < end; r++) {
            col[r] = tile;
        }
    }

}

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;
import static java.lang.Math.pow;



/**
 * Rasterer
 * CS61B, Project 3: https://sp18.datastructur.es/materials/proj/proj3/proj3
 *
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 * NOTE: (image) depth and zoom level are considered synonyms.
 *
 * @author CS61B staff (interface of getMapRaster)
 * @author Emanuel Aguirre (implementation)
 */
public class Rasterer {

    /*
     * The root upper left/lower right longitudes and latitudes represent the bounding box of
     * the root tile, as the images in the img/ folder are scraped.
     * Longitude == x-axis; latitude == y-axis.
     * (Can't make static import because MapServer is in default package).
     */
    private static final double ROOT_ULLON = MapServer.ROOT_ULLON;
    private static final double ROOT_LRLON = MapServer.ROOT_LRLON;
    private static final double ROOT_ULLAT = MapServer.ROOT_ULLAT;
    private static final double ROOT_LRLAT = MapServer.ROOT_LRLAT;

    /* Size of image tiles (can't make static import because MapServer is in default package). */
    private static final int TILE_SIZE = MapServer.TILE_SIZE;

    /* Min and Max Zoom levels of the image tiles available to render an image. */
    private static final int DEPTH_MIN = 0;
    private static final int DEPTH_MAX = 7;

    /* Longitude Distance per Pixel (LonDPP) for each image depth (resolution) available. */
    private static final double[] LONDPP_AT_DEPTH = {
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 0)),
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 1)),
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 2)),
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 3)),
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 4)),
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 5)),
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 6)),
        (ROOT_LRLON - ROOT_ULLON) / (TILE_SIZE * pow(2, 7))
    };

    /* Names of the query parameters. */
    private static final String QUERY_PARAM_ULLON = "ullon";
    private static final String QUERY_PARAM_ULLAT = "ullat";
    private static final String QUERY_PARAM_LRLON = "lrlon";
    private static final String QUERY_PARAM_LRLAT = "lrlat";
    private static final String QUERY_PARAM_WIDTH = "w";
    private static final String QUERY_PARAM_HEIGHT = "h";

    /* Names of the result parameters. */
    private static final String RESULT_RENDER_GRID = "render_grid";
    private static final String RESULT_ULLON = "raster_ul_lon";
    private static final String RESULT_ULLAT = "raster_ul_lat";
    private static final String RESULT_LRLON = "raster_lr_lon";
    private static final String RESULT_LRLAT = "raster_lr_lat";
    private static final String RESULT_DEPTH = "depth";
    private static final String RESULT_QUERY_SUCCESS = "query_success";

    /* Flie format of the image files to use as tiles. */
    private static final String IMAGE_FILE_FORMAT = ".png";


    public Rasterer() { }


    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();

        double ulLon = params.get(QUERY_PARAM_ULLON);
        double ulLat = params.get(QUERY_PARAM_ULLAT);
        double lrLon = params.get(QUERY_PARAM_LRLON);
        double lrLat = params.get(QUERY_PARAM_LRLAT);
        double width = params.get(QUERY_PARAM_WIDTH);

        double queryBoxLonDPP = lonDPP(ulLon, lrLon, (int) width);
        int depth = selectDepth(queryBoxLonDPP);
        boolean success = areValidCoordinates(ulLon, ulLat, lrLon, lrLat);

        if (!success) {
            results.put(RESULT_QUERY_SUCCESS, success);
            results.put(RESULT_RENDER_GRID, null);
            results.put(RESULT_ULLON, 0);
            results.put(RESULT_ULLAT, 0);
            results.put(RESULT_LRLON, 0);
            results.put(RESULT_LRLAT, 0);
            results.put(RESULT_DEPTH, depth);
        } else {
            String[][] renderGrid = selectRenderGrid(ulLon, ulLat, lrLon, lrLat, depth);
            results.put(RESULT_QUERY_SUCCESS, success);
            results.put(RESULT_RENDER_GRID, renderGrid);
            results.put(RESULT_ULLON, boundUpperLeftLon(ulLon, depth));
            results.put(RESULT_ULLAT, boundUpperLeftLat(ulLat, depth));
            results.put(RESULT_LRLON, boundLowerRightLon(lrLon, depth));
            results.put(RESULT_LRLAT, boundLowerRightLat(lrLat, depth));
            results.put(RESULT_DEPTH, depth);
        }
        return results;
    }

    /**
     * Returns true if the coordinate parameters overlap with the the box that contains image data.
     * @param ulLon longitude of the upper-left coordinate.
     * @param ulLat latitude of the upper-left coordinate.
     * @param lrLon longitude of the lower-right coordinate.
     * @param lrLat latitude of the lower-right coordinate.
     * @return true if the query box overlaps over the bounding box.
     */
    public boolean areValidCoordinates(double ulLon, double ulLat, double lrLon, double lrLat) {
        return (ulLon < lrLon && lrLat < ulLat)
                && (ulLon < ROOT_LRLON || ROOT_ULLON < lrLon)
                && (ROOT_LRLAT < ulLat || lrLat < ROOT_ULLAT);
    }

    /**
     * Selects the grid of image tiles to return in order to display the area covered by the
     * bounding box that corresponds to the query box. The query box is given by the upper-left
     * and the lower-right longitude and latitude coordinates passed as parameter.
     * If the coordinates aren't fully contained within the root bounding box, only the tiles
     * that correspond to the area that overlaps with the root bounding box is returned.
     * The tiles to be returned are at the image depth passed as parameter.
     * @return the filenames of a grid of image tiles of the area to render.
     */
    private String[][] selectRenderGrid(
            double ulLon, double ulLat, double lrLon, double lrLat, int depth
    ) {
        int xLeft = xIndexLeft(ulLon, depth);
        int xRight = xIndexRight(lrLon, depth);
        int yUpper = yIndexUpper(ulLat, depth);
        int yLower = yIndexLower(lrLat, depth);

        int gridLengthX = xRight - xLeft + 1;
        int gridLengthY = yLower - yUpper + 1;
        String[][] grid = new String[gridLengthY][gridLengthX];     // [y][x]
        for (int x = 0; x < gridLengthX; x++) {
            for (int y = 0; y < gridLengthY; y++) {
                int xTile = xLeft + x;
                int yTile = yUpper + y;
                grid[y][x] = createTileFileName(xTile, yTile, depth, IMAGE_FILE_FORMAT);
            }
        }
        return grid;
    }

    /**
     * Calculates the bounding upper left longitude of the rastered image.
     */
    private double boundUpperLeftLon(double ulLon, int depth) {
        return ROOT_ULLON + xIndexLeft(ulLon, depth) * tileLonLength(depth);
    }

    /**
     * Calculates  the bounding upper left latitude of the rastered image.
     */
    private double boundUpperLeftLat(double ulLat, int depth) {
        return ROOT_LRLAT
                + (numTilesAtDepth(depth) - yIndexUpper(ulLat, depth)) * tileLatLength(depth);
    }

    /**
     * Calculates  the bounding lower right longitude of the rastered image.
     */
    private double boundLowerRightLon(double lrLon, int depth) {
        return ROOT_ULLON + (xIndexRight(lrLon, depth) + 1) * tileLonLength(depth);
    }

    /**
     * Calculates  the bounding lower right latitude of the rastered image.
     */
    private double boundLowerRightLat(double lrLat, int depth) {
        return ROOT_LRLAT
                + (numTilesAtDepth(depth) - yIndexLower(lrLat, depth) - 1) * tileLatLength(depth);
    }

    /**
     * For a grid of image tiles of depth D (which has 2^D x 2^D elements), returns the leftmost
     * index in the X direction (longitude) that corresponds to the tile that contains the
     * longitude coordinate passed as parameter.
     * @param ulLon is the longitude coordinate of the upper left corner of the query box.
     * @param depth is the image depth (zoom level) of the tiles on which to calculate the index.
     * @return the leftmost index of an x-y grid of tiles, within the grid at the given depth.
     */
    private int xIndexLeft(double ulLon, int depth) {
        double tileLongitudeLength = tileLonLength(depth);
        return (ulLon - ROOT_ULLON) / tileLongitudeLength >= 0
                ? (int) ((ulLon - ROOT_ULLON) / tileLongitudeLength)
                : 0;
    }

    /**
     * For a grid of image tiles of depth D (which has 2^D x 2^D elements), returns the rightmost
     * index in the X direction (longitude) that corresponds to the tile that contains the
     * longitude coordinate passed as parameter.
     * @param lrLon is the longitude coordinate of the lower right corner of the query box.
     * @param depth is the image depth (zoom level) of the tiles on which to calculate the index.
     * @return the rightmost index of an x-y grid of tiles, within the grid at the given depth.
     */
    private int xIndexRight(double lrLon, int depth) {
        double tileLongitudeLength = tileLonLength(depth);
        return (lrLon - ROOT_ULLON) / tileLongitudeLength < numTilesAtDepth(depth)
                ? (int) ((lrLon - ROOT_ULLON) / tileLongitudeLength)
                : numTilesAtDepth(depth) - 1;
    }

    /**
     * For a grid of image tiles of depth D (which has 2^D x 2^D elements), returns the topmost
     * index in the Y direction (latitude) that corresponds to the tile that contains the
     * latitude coordinate passed as parameter.
     * @param ulLat is the latitude coordinate of the upper left corner of the query box.
     * @param depth is the image depth (zoom level) of the tiles on which to calculate the index.
     * @return the topmost index of an x-y grid of tiles, within the grid at the given depth.
     */
    private int yIndexUpper(double ulLat, int depth) {
        double tileLatitudeLength = tileLatLength(depth);
        return (ROOT_ULLAT - ulLat) / tileLatitudeLength >= 0
                ? (int) ((ROOT_ULLAT - ulLat) / tileLatitudeLength)
                : 0;
    }

    /**
     * For a grid of image tiles of depth D (which has 2^D x 2^D elements), returns the bottommost
     * index in the Y direction (latitude) that corresponds to the tile that contains the
     * latitude coordinate passed as parameter.
     * @param lrLat is the latitude coordinate of the lower right corner of the query box.
     * @param depth is the image depth (zoom level) of the tiles on which to calculate the index.
     * @return the bottommost index of an x-y grid of tiles, within the grid at the given depth.
     */
    private int yIndexLower(double lrLat, int depth) {
        double tileLatitudeLength = tileLatLength(depth);
        return (ROOT_ULLAT - lrLat) / tileLatitudeLength < numTilesAtDepth(depth)
                ? (int) ((ROOT_ULLAT - lrLat) / tileLatitudeLength)
                : numTilesAtDepth(depth) - 1;
    }

    /**
     * Generate the filename for an image tile in an X, Y grid.
     */
    private String createTileFileName(int x, int y, int depth, String fileExt) {
        return "d" + depth + "_x" + x + "_y" + y + fileExt;
    }

    /**
     * Calculate the longitudinal distance per pixel (LonDPP) for a given difference of longitudes
     * and a given image width in pixels.
     * This method is to calculate the LonDPP of an image of the surface of the earth between
     * longitudes lon1 and lon2.
     * @param lon1 is one of the longitude values.
     * @param lon2 is one of the longitude values.
     * @param imageWidth is the width, in pixels, of the image.
     * @return the number of units of longitude per pixel, for the given parameters.
     */
    double lonDPP(double lon1, double lon2, int imageWidth) {
        return abs(lon2 - lon1) / imageWidth;
    }

    /**
     * Selects the LonDPP of the images to return.
     * The criteria for selecting LonDPP is: the greatest LonDPP that is less than or equal to
     * the LonDPP of the query box. If the LonDPP passed as parameter is off-bounds, then the
     * nearest LonDPP within bounds is returned.
     * First, calculates the corresponding depth (zoom level), according to LonDPP values available
     * in the array LONDPP_AT_DEPTH (which contains the LonDPP available for the images available
     * at each image depth). Then, based on the depth, returns the corresponding LonDPP.
     * @param lonDPP based on which to select the lonDPP, according to the given criteria.
     * @return one of the fixed values for lonDPP, given by the image resolutions available.
     */
    double selectLonDPP(double lonDPP) {
        int depth = selectDepth(lonDPP);
        return LONDPP_AT_DEPTH[depth];
    }

    /**
     * Selects the image depth (zoom level) that corresponds to the LonDPP selected by the method
     * selectLonDPP, using the same criteria as that method. This method does all the work.
     * NOTE: method selectLonDPP exists to justify the algorithm of this method and for testing.
     */
    private int selectDepth(double lonDPP) {
        int depth = DEPTH_MAX;
        for (int zl = DEPTH_MAX; zl >= DEPTH_MIN; zl--) {
            depth = LONDPP_AT_DEPTH[zl] <= lonDPP ? zl : depth;
        }
        return depth;
    }

    /**
     * Calculates the length in longitude units of a tile at the given depth (zoom level).
     * @param depth is the image depth (zoom level of the image).
     * @return the length in longitude units of a tile at the given depth.
     */
    private double tileLonLength(int depth) {
        return (ROOT_LRLON - ROOT_ULLON) / numTilesAtDepth(depth);
    }

    /**
     * Calculates the length in latitude units of a tile at a given depth (zoom level).
     * @param depth is the image depth (zoom level of the image).
     * @return the length in latitude units of a tile at the given depth.
     */
    private double tileLatLength(int depth) {
        return (ROOT_ULLAT - ROOT_LRLAT) / numTilesAtDepth(depth);
    }

    /**
     * Calculates the number of tiles covered by the bounding box of the root tile at the given
     * depth.
     * Every image depth (zoom level) has double tiles per side as the immediate lower depth.
     * @return the number of tiles covered by the total bounding box at the given depth
     */
    private int numTilesAtDepth(int depth) {
        return (int) pow(2, depth);
    }

}

import java.util.HashMap;
import java.util.Map;

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
 * @author: CS61B staff (interface of getMapRaster)
 * @author: Emanuel Aguirre (implementation)
 */
public class Rasterer {

    /* Min and Max Zoom levels of the image tiles available to render an image. */
    private static final int MIN_ZOOM_LEVEL = 0;
    private static final int MAX_ZOOM_LEVEL = 7;

    /* Longitude Distance per Pixel (LonDPP) for each image depth (resolution) available. */
    private static final double[] LON_DPP_AT_ZOOM = {
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 0)),
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 1)),
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 2)),
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 3)),
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 4)),
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 5)),
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 6)),
        (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 7))
    };

    /* Number of tiles per axis (lon and lat) that comprise the bounding box at each zoom level. */
    private static final int[] TILES_AT_ZOOM_LEVEL = {
        (int) pow(2, 0),
        (int) pow(2, 1),
        (int) pow(2, 2),
        (int) pow(2, 3),
        (int) pow(2, 4),
        (int) pow(2, 5),
        (int) pow(2, 6),
        (int) pow(2, 7),
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


    public Rasterer() {
        // YOUR CODE HERE
    }


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
        // This is for console testing, delete once implemented
        System.out.println("Parameters:");
        System.out.println(params);

        double queryBoxLonDPP =
                lonDPP(params.get("ullon"), params.get("lrlon"), params.get("w").intValue());
        System.out.println("Query Box LonDPP:");
        System.out.println(queryBoxLonDPP);
        System.out.println("LonDPP for every image resolution available:");
        System.out.println(LON_DPP_AT_ZOOM[0]);
        System.out.println(LON_DPP_AT_ZOOM[1]);
        System.out.println(LON_DPP_AT_ZOOM[2]);
        System.out.println(LON_DPP_AT_ZOOM[3]);
        System.out.println(LON_DPP_AT_ZOOM[4]);
        System.out.println(LON_DPP_AT_ZOOM[5]);
        System.out.println(LON_DPP_AT_ZOOM[6]);
        System.out.println(LON_DPP_AT_ZOOM[7]);
        System.out.println("LonDPP that should be applied to the response of the query box:");
        System.out.println(selectLonDPP(queryBoxLonDPP));

        Map<String, Object> results = new HashMap<>();
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");
        return results;
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
        return Math.abs(lon2 - lon1) / imageWidth;
    }

    /**
     * Selects the LonDPP of the images to return.
     * The criteria for selecting LonDPP is: the greatest LonDPP that is less than or equal to
     * the LonDPP of the query box. If the LonDPP passed as parameter is off-bounds, then the
     * nearest LonDPP within bounds is returned.
     * First, calculates the corresponding zoom level, according to the LonDPP values available in
     * the array LON_DPP_AT_ZOOM (which contains the LonDPP available for the images available at
     * each zoom level). The, based of the zoom level, return the corresponding LonDPP.
     * @param lonDPP based on which to select the lonDPP according to the given criteria.
     * @return one of the fixed values for lonDPP, given by the image resolutions available.
     */
    double selectLonDPP(double lonDPP) {
        int zoomLevel = selectZoomLevel(lonDPP);
        return LON_DPP_AT_ZOOM[zoomLevel];
    }

    /**
     * Selects the zoom level that corresponds to the LonDPP selected by the method selectLonDPP,
     * using the same criteria as that method.
     * NOTE: method selectLonDPP exists to justify the algorithm of this method and for testing.
     */
    private int selectZoomLevel(double lonDPP) {
        int zoomLevel = MAX_ZOOM_LEVEL;
        for (int zl = MAX_ZOOM_LEVEL; zl >= MIN_ZOOM_LEVEL; zl--) {
            zoomLevel = LON_DPP_AT_ZOOM[zl] <= lonDPP ? zl : zoomLevel;
        }
        return zoomLevel;
    }

}

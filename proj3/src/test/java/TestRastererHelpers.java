import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static org.junit.Assert.*;



/**
 * Class for testing the helper methods of the Rasterer class.
 * @author: Emanuel Aguirre
 */
public class TestRastererHelpers {

    // Rqeuired by assertEquals(double, double, double)
    private static final double DELTA_FOR_DOUBLE_COMPARISONS = 0.00000001;

    private static Rasterer rasterer;


    @Before
    public void setUp() throws Exception {
        rasterer = new Rasterer();
    }


    /* Tests for: rasterer.lonDPP */

    @Test
    public void testLonDPP() {
        double lon1 = -100.0;
        double lon2 = -101.0;
        int w = 356;
        assertEquals(
                abs(lon1 - lon2) / w,
                rasterer.lonDPP(lon1, lon2, w),
                DELTA_FOR_DOUBLE_COMPARISONS);
        assertEquals(
                abs(lon2 - lon1) / w,
                rasterer.lonDPP(lon1, lon2, w),
                DELTA_FOR_DOUBLE_COMPARISONS);
    }


    /* Tests for: rasterer.selectLonDPP */

    @Test
    // A LonDPP above the greatest LonDPP should return the greatest LonDPP.
    public void testLonDPPAboveUpperBound() {
        double lonDPPGreaterThanGreatest =
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, -1));
        double expected =
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 0));
        assertEquals(
                expected,
                rasterer.selectLonDPP(lonDPPGreaterThanGreatest),
                DELTA_FOR_DOUBLE_COMPARISONS);
    }

    // A LonDPP below the smallest LonDPP should return the smallest LonDPP.
    @Test
    public void testLonDPPBelowLowerBound() {
        double lonDPPLessThanSmallest =
            (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 8));
        double expected =
                (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * pow(2, 7));
        assertEquals(
                expected,
                rasterer.selectLonDPP(lonDPPLessThanSmallest),
                DELTA_FOR_DOUBLE_COMPARISONS);
    }

    @Test
    public void testSelectLonDPP() {
        // lonDPPA = 5.789279937744141E-6; selectLonDPP = 5.364418029785156E-6 is lower
        double lonA1 = -122.21835720062256;
        double lonA2 = -122.27625;
        int wA = 10000;
        double lonDPPA = rasterer.lonDPP(lonA1, lonA2, wA);
        // LonDDPB = 4.291534423828125E-5; selectLonDPP = 4.291534423828125E-5 is equals
        double lonB1 = -122.21835720062256;
        double lonB2 = -122.27625;
        int wB = 1349;
        double lonDPPB = rasterer.lonDPP(lonB1, lonB2, wB);
        // lonDPPC = 8.640716324991255E-5; selectLonDPP = 8.58306884765625E-5 is lower
        double lonC1 = -122.21835720062256;
        double lonC2 = -122.27625;
        int wC = 670;
        double lonDPPC = rasterer.lonDPP(lonC1, lonC2, wC);
        // lonDPPD = 1.754327253861861E-4; selectLonDPP = 1.71661376953125E-4 is lower
        double lonD1 = -122.21835720062256;
        double lonD2 = -122.27625;
        int wD = 330;
        double lonDPPD = rasterer.lonDPP(lonD1, lonD2, wD);

        assertEquals(
                5.364418029785156E-6,
                rasterer.selectLonDPP(lonDPPA),
                DELTA_FOR_DOUBLE_COMPARISONS);
        assertEquals(
                4.291534423828125E-5,
                rasterer.selectLonDPP(lonDPPB),
                DELTA_FOR_DOUBLE_COMPARISONS);
        assertEquals(
                8.58306884765625E-5,
                rasterer.selectLonDPP(lonDPPC),
                DELTA_FOR_DOUBLE_COMPARISONS);
        assertEquals(
                1.71661376953125E-4,
                rasterer.selectLonDPP(lonDPPD),
                DELTA_FOR_DOUBLE_COMPARISONS);
    }


    /* Tests for: rasterer.areValidCoordinates */

    // All coordinates are within bounds, and upper left and lower right are in good relative pos.
    @Test
    public void testAreValidCoordinatesAllInBounds() {
        double ulLon = MapServer.ROOT_ULLON + 0.02;
        double ulLat = MapServer.ROOT_ULLAT - 0.02;
        double lrLon = MapServer.ROOT_LRLON - 0.02;
        double lrLat = MapServer.ROOT_LRLAT + 0.02;
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // All coordinates are within bounds, but right coordinate is smaller than left.
    @Test
    public void testAreValidCoordinatesAllInBoundsRightSmallerThanLeft() {
        double ulLon = MapServer.ROOT_LRLON - 0.02;         // x
        double ulLat = MapServer.ROOT_ULLAT - 0.02;
        double lrLon = MapServer.ROOT_ULLON + 0.02;         // x
        double lrLat = MapServer.ROOT_LRLAT + 0.02;
        assertFalse(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // All coordinates are within bounds, but upper coordinate is smaller than lower.
    @Test
    public void testAreValidCoordinatesAllInBoundsUpperSmallerThanLower() {
        double ulLon = MapServer.ROOT_ULLON + 0.02;
        double ulLat = MapServer.ROOT_LRLAT + 0.02;         // x
        double lrLon = MapServer.ROOT_LRLON - 0.02;
        double lrLat = MapServer.ROOT_ULLAT - 0.02;         // x
        assertFalse(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Only Upper Left coordinates are within bounds.
    @Test
    public void testAreValidCoordinatesUpperLeftInBounds() {
        double ulLon = MapServer.ROOT_ULLON + 0.02;
        double ulLat = MapServer.ROOT_ULLAT - 0.02;
        double lrLon = MapServer.ROOT_LRLON + 1.00;         // x
        double lrLat = MapServer.ROOT_LRLAT - 1.00;         // x
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Only Lower Left coordinates are within bounds.
    @Test
    public void testAreValidCoordinatesLowerLeftInBounds() {
        double ulLon = MapServer.ROOT_ULLON + 0.02;
        double ulLat = MapServer.ROOT_ULLAT + 1.00;         // x
        double lrLon = MapServer.ROOT_LRLON + 1.00;         // x
        double lrLat = MapServer.ROOT_LRLAT + 0.02;
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Only Upper Right coordinates are within bounds.
    @Test
    public void testAreValidCoordinatesUpperRightInBounds() {
        double ulLon = MapServer.ROOT_ULLON - 1.00;         // x
        double ulLat = MapServer.ROOT_ULLAT - 0.02;
        double lrLon = MapServer.ROOT_LRLON - 0.02;
        double lrLat = MapServer.ROOT_LRLAT - 1.00;         // x
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Only Lower Right coordinates are within bounds.
    @Test
    public void testAreValidCoordinatesLowerRightInBounds() {
        double ulLon = MapServer.ROOT_ULLON - 1.00;         // x
        double ulLat = MapServer.ROOT_ULLAT + 1.00;         // x
        double lrLon = MapServer.ROOT_LRLON - 0.02;
        double lrLat = MapServer.ROOT_LRLAT + 0.02;
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Out of bounds to the right.
    @Test
    public void testAreValidCoordinatesRightOutOfBounds() {
        double ulLon = MapServer.ROOT_ULLON + 0.02;
        double ulLat = MapServer.ROOT_ULLAT - 0.02;
        double lrLon = MapServer.ROOT_LRLON + 1.00;         // x
        double lrLat = MapServer.ROOT_LRLAT + 0.02;
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Out of bounds to the left.
    @Test
    public void testAreValidCoordinatesLeftOutOfBounds() {
        double ulLon = MapServer.ROOT_ULLON - 1.00;         // x
        double ulLat = MapServer.ROOT_ULLAT - 0.02;
        double lrLon = MapServer.ROOT_LRLON - 0.02;
        double lrLat = MapServer.ROOT_LRLAT + 0.02;
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Out of bounds above.
    @Test
    public void testAreValidCoordinatesAboveOutOfBounds() {
        double ulLon = MapServer.ROOT_ULLON + 0.02;
        double ulLat = MapServer.ROOT_ULLAT + 1.00;         // x
        double lrLon = MapServer.ROOT_LRLON - 0.02;
        double lrLat = MapServer.ROOT_LRLAT + 0.02;
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

    // Out of bounds below.
    @Test
    public void testAreValidCoordinatesBelowOutOfBounds() {
        double ulLon = MapServer.ROOT_ULLON + 0.02;
        double ulLat = MapServer.ROOT_ULLAT - 0.02;
        double lrLon = MapServer.ROOT_LRLON - 0.02;
        double lrLat = MapServer.ROOT_LRLAT - 1.00;         // x
        assertTrue(rasterer.areValidCoordinates(ulLon, ulLat, lrLon, lrLat));
    }

}

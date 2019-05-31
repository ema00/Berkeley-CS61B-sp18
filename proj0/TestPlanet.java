import java.math.*;

/**
 *  Tests Planet
 */
public class TestPlanet {

    /**
     *  Tests calcForceExertedByX and calcForceExertedByY.
     */
    public static void main(String[] args) {
        checkCalcNetForceExertedByX();
        checkCalcNetForceExertedByY();
    }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
        }
    }

    private static Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0,
            "mercury.gif");
    private static Planet p2 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0,
            "venus.gif");
    private static Planet[] planets = new Planet[]{p1, p2};

    /**
     *  Checks the Planet class to make sure calcNetForceExertedByX works.
     */
    private static void checkCalcNetForceExertedByX() {
        double fxP1 = p1.calcNetForceExertedByX(planets);
        double fxP2 = p2.calcNetForceExertedByX(planets);

        checkEquals(fxP1, -fxP2, "calcNetForceExertedByX()", 0.01);
    }

    /**
     *  Checks the Planet class to make sure checkCalcNetForceExertedByY works.
     */
    private static void checkCalcNetForceExertedByY() {
        double fyP1 = p1.calcNetForceExertedByY(planets);
        double fyP2 = p2.calcNetForceExertedByY(planets);

        checkEquals(fyP1, -fyP2, "calcNetForceExertedByY()", 0.01);
    }

}

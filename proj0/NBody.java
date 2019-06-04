import java.io.File;


/**
 * Nbody
 * Runs the simulation of mutual forces between planets.
 */

public class NBody {

    private static final String BACKGROUND = "starfield.jpg";
    private static final String IMAGES_PATH = "images/";

    private static double T;
    private static double dt;
    private static String filename;
    private static double radius;
    private static Planet[] planets;


    public static void main(String[] args) {
        T = Double.parseDouble(args[0]);
        dt = Double.parseDouble(args[1]);
        filename = args[2];
        radius = readRadius(filename);
        planets = readPlanets(filename);

        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        StdDraw.clear();
        StdDraw.picture(0, 0, IMAGES_PATH + BACKGROUND);
        StdDraw.show();
        drawPlanets(planets);
        for (double time = 0; time < T; time += dt) {
            double[] xForces = calcNetXForces(planets);
            double[] yForces = calcNetYForces(planets);
            updatePlanetsPositions(planets, xForces, yForces);
            StdDraw.clear();
            StdDraw.picture(0, 0, IMAGES_PATH + BACKGROUND);
            drawPlanets(planets);
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf(toStr());
    }

    /**
     * Updates the positions of all the planets in the universe.
     * @param planets array containing all the planets in the universe.
     * @param xForces array containing all the forces exerted in the X direction for each Planet in planets array.
     * @param yForces array containing all the forces exerted in the Y direction for each Planet in planets array.
     */
    private static void updatePlanetsPositions(Planet[] planets, double[] xForces, double[] yForces) {
        for (int i = 0; i < planets.length; i++) {
            planets[i].update(dt, xForces[i], yForces[i]);
        }
    }

    /**
     * Calculates the forces exerted in the X direction on planets[i] by the other planets in the planets array.
     * @param planets array containing all the planets in the universe.
     * @return the forces exerted in the X direction on each planet by all the other planets.
     */
    private static double[] calcNetXForces(Planet[] planets) {
        double[] xForces = new double[planets.length];
        for (int i = 0; i < planets.length; i++) {
            xForces[i] = planets[i].calcNetForceExertedByX(planets);
        }
        return xForces;
    }

    /**
     * Calculates the forces exerted in the Y direction on planets[i] by the other planets in the planets array.
     * @param planets array containing all the planets in the universe.
     * @return the forces exerted in the Y direction on each planet by all the other planets.
     */
    private static double[] calcNetYForces(Planet[] planets) {
        double[] yForces = new double[planets.length];
        for (int i = 0; i < planets.length; i++) {
            yForces[i] = planets[i].calcNetForceExertedByY(planets);
        }
        return yForces;
    }

    /**
     * Draws all the planets in the universe.
     * @param planets are all the planets to be drawn.
     */
    private static void drawPlanets(Planet[] planets) {
        for (Planet p : planets) {
            p.draw();
        }
    }

    /**
     * Reads the radius of the universe in the file containing data about the universe to be simulated.
     * @param filename is the file that contains data about the universe to be simulated.
     * @return the radius of the universe, in metres.
     */
    public static double readRadius(String filename) {
        File file = new File(filename);
        In in = new In(file);
        in.readLine();
        double radius = in.readDouble();
        in.close();
        return radius;
    }

    /**
     * Reads the planets in the file containing data about the universe to be simulated.
     * @param filename is the file that contains data about the universe to be simulated.
     * @return an array of planets in the file passed as parameter.
     */
    public static Planet[] readPlanets(String filename) {
        File file = new File(filename);
        In in = new In(file);
        Planet[] planets;
        int n = in.readInt();
        planets = new Planet[n];
        in.readDouble();
        for (int i = 0; i < n; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            double vx = in.readDouble();
            double vy = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = IMAGES_PATH + in.readString();
            planets[i] = new Planet(x, y, vx, vy, mass, imgFileName);
        }
        in.close();
        return planets;
    }

    /**
     * Converts the state of the simulation to its String representation.
     * @return a String containing the number of planets, the radius of the universe and the position of the planets.
     */
    public static String toStr() {
        String str = "";
        str = str + String.format("%d\n", planets.length);
        str = str + String.format("%.2e\n", radius);
        for (Planet p: planets) {
            str = str + String.format("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
        }
        return str.replaceAll(IMAGES_PATH, " ");
    }
}

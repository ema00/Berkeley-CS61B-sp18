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
        StdDraw.clear();
        StdDraw.picture(0, 0, IMAGES_PATH + BACKGROUND);
        StdDraw.show();

        // DRAW A SINGLE PLANET
        planets[0].draw();
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
}

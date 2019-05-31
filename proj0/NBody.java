import java.io.File;


/**
 * Nbody
 * Runs the simulation of mutual forces between planets.
 */

public class NBody {

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
            String imgFileName = in.readString();
            planets[i] = new Planet(x, y, vx, vy, mass, imgFileName);
        }
        in.close();
        return planets;
    }
}

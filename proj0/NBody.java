import java.io.File;


/**
 * Nbody
 * Runs the simulation of mutual forces between planets.
 */

public class NBody {

    /**
     *
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

}

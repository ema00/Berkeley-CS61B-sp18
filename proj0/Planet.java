/**
 * Planet
 * Representation of a planet, its position, velocity, and mass.
 */

public class Planet {

    /* Gravitational constant G = 6.67x10^-11 [M * m^2 / kg^2] */
    public static final double G = 6.67e-11;

    /* Current x position */
    public double xxPos;
    /* Current y position */
    public double yyPos;
    /* Current velocity in the x direction */
    public double xxVel;
    /* Current velocity in the y direction */
    public double yyVel;
    /* mass */
    public double mass;
    /* Name of the file that corresponds to the image that depicts the planet  */
    public String imgFileName;


    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }


    /**
     * Calculate distance between this planet and another planet.
     * @param p is the other planet whose distance is calculated.
     * @return distance between planet p, passed as parameter, and this planet.
     */
    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * Calculate the force exerted on this planet by another planet.
     * @param p is the planet whose force is exerted on this planet.
     * @return the force exerted on this planet by planet p, passed as parameter.
     */
    public double calcForceExertedBy(Planet p) {
        double d = this.calcDistance(p);
        return G * this.mass * p.mass / Math.pow(d, 2);
    }

}

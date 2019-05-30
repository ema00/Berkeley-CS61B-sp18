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
     * Calculates and updates the change in velocity and position, based on the net exerted force on this planet.
     * @param dt is the lapse of time used for calculating the change in velocity.
     * @param fx net force exerted on this planet in the X direction.
     * @param fy net force exerted on this planet in the Y direction.
     */
    public void update(double dt, double fx, double fy) {
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    /**
     * Calculate the force exerted on this planet by other planets, in the X direction.
     * @param planets are the planets whose force is exerted on this planet.
     * @return the total force exerted on this planet by planets, in the X direction.
     */
    public double calcNetForceExertedByX(Planet[] planets) {
        double netForceX = 0;
        for (Planet p : planets) {
            if (p == this) { continue; }
            netForceX += calcForceExertedByX(p);
        }
        return netForceX;
    }

    /**
     * Calculate the force exerted on this planet by other planets, in the Y direction.
     * @param planets are the planets whose force is exerted on this planet.
     * @return the total force exerted on this planet by planets, in the Y direction.
     */
    public double calcNetForceExertedByY(Planet[] planets) {
        double netForceY = 0;
        for (Planet p : planets) {
            if (p == this) { continue; }
            netForceY += calcForceExertedByY(p);
        }
        return netForceY;
    }

    /**
     * Calculate the force exerted on this planet by another planet, in the X direction.
     * @param p is the planet whose force is exerted on this planet.
     * @return the force exerted on this planet by planet p, in the X direction.
     */
    public double calcForceExertedByX(Planet p) {
        double f = calcForceExertedBy(p);
        double d = calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return f * dx / d;
    }

    /**
     * Calculate the force exerted on this planet by another planet, in the Y direction.
     * @param p is the planet whose force is exerted on this planet.
     * @return the force exerted on this planet by planet p, in the Y direction.
     */
    public double calcForceExertedByY(Planet p) {
        double f = calcForceExertedBy(p);
        double d = calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        return f * dy / d;
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

}

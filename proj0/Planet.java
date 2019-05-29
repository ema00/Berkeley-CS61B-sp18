/**
 * Planet
 * Representation of a planet, its position, velocitiy, and mass.
 */

public class Planet {

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

}

import static java.lang.Math.pow;
import java.awt.Color;

import edu.princeton.cs.algs4.Picture;



/**
 * SeamCarver
 * CS61B, Homework 5: https://sp18.datastructur.es/materials/hw/hw5/hw5
 *
 * @author Emanuel Aguirre
 */
public class SeamCarver {

    private Picture picture;


    public SeamCarver(Picture picture) {
        this.picture = picture;
    }


    /**
     * Current picture, on which to perform seam carving.
     */
    public Picture picture() {
        return this.picture;
    }

    /**
     * Width of current picture.
     */
    public int width() {
        return picture.width();
    }

    /**
     * Height of current picture.
     */
    public int height() {
        return picture.height();
    }

    /**
     * Energy gradient of pixel at column x and row y. It is calculated as the sum of the energy
     * gradients in the x and y directions.
     */
    public double energy(int x, int y) {
        if (x < 0 || width() < x) {
            throw new IndexOutOfBoundsException("Pixel is out of bound in the x direction.");
        }
        if(y < 0 || height() < y) {
            throw new IndexOutOfBoundsException("Pixel is out of bound in the y direction.");

        }
        int width = width();
        int height = height();
        double xGradient = energyGradientX(x, y, width);
        double yGradient = energyGradientY(x, y, height);
        return xGradient + yGradient;
    }

    /**
     * Calculates the energy gradient for a pixel, in the x direction.
     * Rx(x,y)^2 + Gx(x,y)^2 + Bx(x,y)^2, where Rx, Gx and Bx are the differences in red, green
     * and blue between the pixels at x-1 and x+1. For pixels that are on the limits (0 and W-1),
     * W-1 is used in the case of 0, and 0 is used in the case of W-1 (it is wrapped around).
     */
    private double energyGradientX(int x, int y, int width) {
        int x1 = (x - 1 + width) % width;
        int x2 = (x + 1 + width) % width;
        Color color1 = picture.get(x1, y);
        Color color2 = picture.get(x2, y);
        int deltaRed = color2.getRed() - color1.getRed();
        int deltaGreen = color2.getGreen() - color1.getGreen();
        int deltaBlue = color2.getBlue() - color1.getBlue();
        return pow(deltaRed, 2) + pow(deltaGreen, 2) + pow(deltaBlue, 2);
    }

    /**
     * Calculates the energy gradient for a pixel, in the y direction.
     * Ry(x,y)^2 + Gy(x,y)^2 + By(x,y)^2, where Ry, Gy and By are the differences in red, green
     * and blue between the pixels at y-1 and y+1. For pixels that are on the limits (0 and H-1),
     * H-1 is used in the case of 0, and 0 is used in the case of H-1 (it is wrapped around).
     */
    private double energyGradientY(int x, int y, int height) {
        int y1 = (y - 1 + height) % height;
        int y2 = (y + 1 + height) % height;
        Color color1 = picture.get(x, y1);
        Color color2 = picture.get(x, y2);
        int deltaRed = color2.getRed() - color1.getRed();
        int deltaGreen = color2.getGreen() - color1.getGreen();
        int deltaBlue = color2.getBlue() - color1.getBlue();
        return pow(deltaRed, 2) + pow(deltaGreen, 2) + pow(deltaBlue, 2);
    }

    /**
     * Sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        // TODO
        return new int[0];
    }

    /**
     * Sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        // TODO
        return new int[0];
    }

    /**
     * Remove horizontal seam from picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        // TODO
    }

    /**
     * Remove vertical seam from picture.
     */
    public void removeVerticalSeam(int[] seam) {
        // TODO
    }

}

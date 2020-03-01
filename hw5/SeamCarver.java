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
        if (y < 0 || height() < y) {
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
        int width = width();
        int height = height();
        double[][] energy = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                energy[x][y] = energy(x, y);
            }
        }

        double[][] minAccumulatedEnergy = accumulateEnergyOnX(energy);
        return minEnergySeamX(minAccumulatedEnergy, width, height);
    }

    /**
     * Accumulate energy of every pixel along the x axis, starting from the leftmost row; and
     * store it in an array of the same dimensions as the original array, that is returned.
     */
    private double[][] accumulateEnergyOnX(double[][] energy) {
        int width = energy.length;
        int height = energy[0].length;
        double[][] minAccumulatedEnergy = new double[width][];
        for (int x = 0; x < width; x++) {
            minAccumulatedEnergy[x] = energy[x].clone();
        }
        for (int x = 1; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int yPrevAbove = Math.max(y - 1, 0);
                int yPrevInline = y;
                int yPrevBelow = Math.min(y + 1, height - 1);
                int xPrev = Math.max(x - 1, 0);
                double minPrevEnergy = min(
                        energy[xPrev][yPrevAbove],
                        energy[xPrev][yPrevInline],
                        energy[xPrev][yPrevBelow]);
                minAccumulatedEnergy[x][y] = minPrevEnergy + energy[x][y];
            }
        }
        return minAccumulatedEnergy;
    }

    /**
     * Find the horizontal seam of minimum energy.
     * The seam is found finding pixel with the lowest energy on the rightmost column, then
     * finding the pixels with the lowest energy from right to left. For a given pixel, the
     * pixels that are considered as continuation of the seam, are the the 3 pixels to the right
     * of the pixel (above-left, center-left, below-left).
     */
    private int[] minEnergySeamX(double[][] minAccumulatedEnergy, int width, int height) {
        int[] y = new int[width];
        // find minimum energy for the last column (rightmost)
        double minEnergy = minAccumulatedEnergy[width - 1][0];
        for (int y0 = 1; y0 < height; y0++) {
            if (minAccumulatedEnergy[width - 1][y0] < minEnergy) {
                y[width - 1] = y0;
                minEnergy = minAccumulatedEnergy[width - 1][y0];
            }
        }
        // find minimum energy for the other columns, in decreasing order
        int yPrev = y[width - 1];
        for (int x = width - 2; x >= 0; x--) {
            int yAbove = Math.max(yPrev - 1, 0);
            int yInline = yPrev;
            int yBelow = Math.min(yPrev + 1, height - 1);
            minEnergy = min(
                    minAccumulatedEnergy[x][yAbove],
                    minAccumulatedEnergy[x][yInline],
                    minAccumulatedEnergy[x][yBelow]);
            if (minEnergy == minAccumulatedEnergy[x][yAbove]) {
                y[x] = yAbove;
            } else if (minEnergy == minAccumulatedEnergy[x][yBelow]) {
                y[x] = yBelow;
            } else {
                y[x] = yInline;
            }
            yPrev = y[x];
        }
        return y;
    }

    /**
     * Returns the minimum of 3 doubles.
     */
    private double min(double v1, double v2, double v3) {
        return Math.min(v1, Math.min(v2, v3));
    }

    /**
     * Sequence of indices for vertical seam.
     * Finds the seam by using the methods for finding the horizontal seam.
     */
    public int[] findVerticalSeam() {
        int width = height();
        int height = width();
        double[][] energy = new double[width][height];
        // transpose array
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                energy[(width - 1) - x][y] = energy(y, x);
            }
        }
        // find seam
        double[][] minAccumulatedEnergy = accumulateEnergyOnX(energy);
        int[] minEnergySeam = minEnergySeamX(minAccumulatedEnergy, width, height);
        // reverse and return seam found
        for (int i = 0; i < minEnergySeam.length / 2; i++) {
            int temp = minEnergySeam[i];
            minEnergySeam[i] = minEnergySeam[minEnergySeam.length - i - 1];
            minEnergySeam[minEnergySeam.length - i - 1] = temp;
        }
        return minEnergySeam;
    }

    /**
     * Remove horizontal seam from picture.
     */
    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }

    /**
     * Remove vertical seam from picture.
     */
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }

}

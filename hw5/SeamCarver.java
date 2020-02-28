


/**
 * SeamCarver
 * CS61B, Homework 5: https://sp18.datastructur.es/materials/hw/hw5/hw5
 *
 * @author Emanuel Aguirre
 */
public class SeamCarver {

    private Picture picture;


    public SeamCarver(Picture picture) {

    }


    /**
     * Current picture, on which to perform seam carving.
     */
    public Picture picture() {
        // TODO
        return this.picture;
    }

    /**
     * Width of current picture.
     */
    public int width() {
        // TODO
        return 0;
    }

    /**
     * Height of current picture.
     */
    public int height() {
        // TODO
        return 0;
    }

    /**
     * Energy of pixel at column x and row y.
     */
    public double energy(int x, int y) {
        // TODO
        return 0.0;
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

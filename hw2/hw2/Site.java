package hw2;

import java.util.Set;
import java.util.TreeSet;



/**
 * Site
 * Represents a site in a grid of sites, in the context of the percolation theory.
 * Provides methods to generate a grid of (NxN) sites, and to get the neighbors of a site.
 * Also implements methods for total ordering of the instances (Comparable interface).
 * This class serves to be used exclusively by the Percolation class.
 */
class Site implements Comparable<Site> {

    final int id;
    final int row;
    final int col;
    private boolean open;
    /* Grid to which the site belongs to; necessary to access its neighbors. */
    private final Site[][] grid;


    private Site(int id, Site[][] grid, int row, int col) {
        this.id = id;
        this.grid = grid;
        this.row = row;
        this.col = col;
        this.open = false;
    }


    boolean isOpen() {
        return open;
    }

    void open() {
        this.open = true;
    }

    /**
     * Constructs and initializes a grid of N by N Sites.
     * Id generation is compatible with Disjoint Sets (Union Find) implementation.
     */
    static Site[][] getGridOfSites(int size) {
        final Site[][] grid = new Site[size][size];
        int idCounter = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int siteId = idCounter++;
                grid[row][col] = new Site(siteId, grid, row, col);
            }
        }
        return grid;
    }

    Iterable<Site> getNeighbors() {
        Set<Site> neighbors = new TreeSet<>();  // above, below, and on both sides
        int size = grid.length;

        if (row - 1 >= 0) {
            neighbors.add(grid[row - 1][col]);
        }
        if (row + 1 < size) {
            neighbors.add(grid[row + 1][col]);
        }
        if (col - 1 >= 0) {
            neighbors.add(grid[row][col - 1]);
        }
        if (col + 1 < size) {
            neighbors.add(grid[row][col + 1]);
        }
        return neighbors;
    }

    @Override
    public int compareTo(Site other) {
        if (other == null) {
            throw new NullPointerException("Can not compare object of type Site to null.");
        }
        return this.id - other.id;
    }

}

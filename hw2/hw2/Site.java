package hw2;

import java.util.Set;
import java.util.TreeSet;



/**
 * Site
 * Represents a site in a grid of sites, in the context of the percolation theory.
 * Provides methods to generate a grid of (NxN) sites, and to get the neighbors of a site.
 * Also implements methods for total ordering of the instances.
 * This class serves to be used exclusively by the Percolation class.
 */
class Site implements Comparable<Site> {

    final int id;
    private boolean open;
    private Set<Site> neighbors;    // above, below, and on both sides


    private Site(int id) {
        this.id = id;
        this.open = false;
        neighbors = new TreeSet<>();
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
                grid[row][col] = new Site(siteId);
            }
        }
        generateNeighbors(grid);
        return grid;
    }

    /**
     * Generates the neighbors for each Site of a grid of Site instances.
     * For this domain, neighbors are located above, below, and at both sides.
     */
    private static void generateNeighbors(Site[][] sites) {
        int size = sites.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Site site = sites[row][col];
                if (row - 1 >= 0) {
                    site.addNeighbor(sites[row - 1][col]);
                }
                if (row + 1 < size) {
                    site.addNeighbor(sites[row + 1][col]);
                }
                if (col - 1 >= 0) {
                    site.addNeighbor(sites[row][col - 1]);
                }
                if (col + 1 < size) {
                    site.addNeighbor(sites[row][col + 1]);
                }
            }
        }
    }

    private void addNeighbor(Site neighbor) {
        if (neighbors.size() > 3) {
            throw new RuntimeException("Number of neighbors for Site can't be > 4.");
        }
        neighbors.add(neighbor);
    }

    Set<Site> getNeighbors() {
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

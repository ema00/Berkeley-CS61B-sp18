/*
 * CS 61B - Spring 2018
 * Homework 2: Percolation
 * https://sp18.datastructur.es/materials/hw/hw2/hw2
 */

package hw2;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

//import edu.princeton.cs.algs4.WeightedQuickUnionUF;



/**
 * Percolation
 * Model of a percolation system using an N-by-N grid of sites.
 * Each site is either open or blocked. A full site is an open site that can be connected to an
 * open site in the top row via a chain of neighboring (left, right, up, down) open sites.
 * The system percolates if there is a full site in the bottom row.
 *
 * This implementation uses Sets and Maps. This implementation is slower than the one that uses
 * Disjoint Sets (Union Find).
 */
public class PercolationNaive {

    private final int size;
    private int numberOpenSites = 0;
    private final Site[][] sites;
    private final Map<Integer, Set<Site>> openSites;


    // create N-by-N grid, with all sites initially blocked
    public PercolationNaive(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be a natural number.");
        }
        size = N;
        sites = Site.getGridOfSites(N);
        openSites = new TreeMap<Integer, Set<Site>>();
    }


    /**
     * Open the site (row, col) if it is not open already.
     */
    public void open(int row, int col) {
        checkBounds(row, col);
        if (!isOpen(row, col)) {
            Site site = sites[row][col];
            site.open();
            numberOpenSites += 1;
            connectToOpenSites(site);
        }
    }

    /**
     * Is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        Site site = sites[row][col];
        return site.isOpen();
    }

    /**
     * Is the site (row, col) full?
     * First, find the set to which the Site belongs to.
     * Then, find if any of the sites of the top row belongs to the same set.
     */
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        Site site = sites[row][col];
        Site[] topRowSites = sites[0];
        Set<Site> openSiteSet = null;
        for (int osKey : openSites.keySet()) {
            Set<Site> openSite = openSites.get(osKey);
            if (openSite.contains(site)) {
                openSiteSet = openSite;
                break;
            }
        }
        if (openSiteSet != null) {
            for (int column = 0; column < size; column++) {
                if (openSiteSet.contains(topRowSites[column])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Number of open sites.
     */
    public int numberOfOpenSites() {
        return numberOpenSites;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        for (int column = 0; column < size; column++) {
            if (isFull(size - 1, column)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if site at row, col is within bounds of the grid.
     */
    private void checkBounds(int row, int col) {
        if (row < 0 || col < 0 || size <= row || size <= col) {
            throw new IndexOutOfBoundsException("row or column are out of bounds.");
        }
    }

    /**
     * Connects an open site to any neighboring open sites.
     * First, creates a new full site comprised by the new node only.
     * Second, merges full sites which are connected by the newly open site into the new full site.
     * And deletes the previously unconnected sites, if any.
     * Precondition: the site must be open just before calling this method.
     */
    private void connectToOpenSites(Site site) {
        int newOpenSiteKey = numberOfOpenSites();
        Set<Site> newOpenSite = new TreeSet<>();
        Iterable<Site> neighbors = site.getNeighbors();

        newOpenSite.add(site);

        for (Site neighbor : neighbors) {
            Integer[] osKeys = new Integer[openSites.size()];
            openSites.keySet().toArray(osKeys);
            for (int osKey : osKeys) {
                Set<Site> openSite = openSites.get(osKey);
                if (openSite.contains(neighbor)) {
                    newOpenSite.addAll(openSite);
                    openSites.remove(osKey);
                }
            }
        }

        openSites.put(newOpenSiteKey, newOpenSite);
    }


    // use for unit testing (not required)
    public static void main(String[] args) {
    }

}

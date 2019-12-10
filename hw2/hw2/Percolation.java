/*
 * CS 61B - Spring 2018
 * Homework 2: Percolation
 * https://sp18.datastructur.es/materials/hw/hw2/hw2
 */

package hw2;

import java.util.Set;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;



/**
 * Percolation
 * Model of a percolation system using an N-by-N grid of sites.
 * Each site is either open or blocked. A full site is an open site that can be connected to an
 * open site in the top row via a chain of neighboring (left, right, up, down) open sites.
 * The system percolates if there is a full site in the bottom row.
 *
 * This implementation uses Disjoint Sets (Union Find). This implementation is faster than the
 * one that uses Trees and Maps.
 */
public class Percolation {

    private final int size;
    private int numberOpenSites = 0;
    private final Site[][] sites;
    /* Union-Find allows to group open sites in disjoint sets, and to find if 2 sites
    are connected in log N time. */
    private final WeightedQuickUnionUF openSites;
    /* Sentinel identifier to which all open sites at the top row are connected. */
    private final int topRowOpenSitesSentinel;
    /* Sentinel identifier to which all open sites at the bottom row are connected. */
    private final int bottomRowOpenSitesSentinel;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be a natural number.");
        }
        size = N;
        sites = Site.getGridOfSites(N);
        openSites = new WeightedQuickUnionUF(N * N + 2);
        topRowOpenSitesSentinel = (N * N - 1) + 1;
        bottomRowOpenSitesSentinel = (N * N - 1) + 2;
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
     * Check if the site is connected to any of the sites in the top row.
     */
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        Site site = sites[row][col];
        return openSites.connected(site.id, topRowOpenSitesSentinel);
    }

    /**
     * Number of open sites.
     */
    public int numberOfOpenSites() {
        return numberOpenSites;
    }

    /**
     * Does the system percolate?
     * Checks if any of the bottom rows is full (connected to any top row).
     */
    public boolean percolates() {
        return openSites.connected(bottomRowOpenSitesSentinel, topRowOpenSitesSentinel);
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
     * If the site is in the top (bottom) row, connects the site to all the open sites in
     * that same row.
     * Precondition: the site must be open just before calling this method.
     */
    private void connectToOpenSites(Site site) {
        Set<Site> neighbors = site.getNeighbors();
        for (Site neighbor : neighbors) {
            if (neighbor.isOpen()) {
                openSites.union(site.id, neighbor.id);
            }
        }
        // connect to sentinel of top (or bottom) row open sites, if corresponds
        if (site.row == 0) {
            openSites.union(site.id, topRowOpenSitesSentinel);
        }
        if (site.row == size - 1) {
            openSites.union(site.id, bottomRowOpenSitesSentinel);
        }
    }


    // use for unit testing (not required)
    public static void main(String[] args) {
    }

}

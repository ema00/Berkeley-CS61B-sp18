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
    private final WeightedQuickUnionUF openSites;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid size must be a natural number.");
        }
        size = N;
        sites = Site.getGridOfSites(N);
        openSites = new WeightedQuickUnionUF(N * N);
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
        Site[] topRowSites = sites[0];

        for (int column = 0; column < size; column++) {
            Site topRowSite = topRowSites[column];
            if (topRowSite.isOpen() && areConnected(site, topRowSite)) {
                return true;
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
     * Checks if any of the bottom rows is full (connected to any top row).
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
     * Precondition: the site must be open just before calling this method.
     */
    private void connectToOpenSites(Site site) {
        Set<Site> neighbors = site.getNeighbors();
        for (Site neighbor : neighbors) {
            if (neighbor.isOpen()) {
                openSites.union(site.id, neighbor.id);
            }
        }
    }

    /**
     * Checks if two sites are connected by a chain of neighbor sites.
     */
    private boolean areConnected(Site s1, Site s2) {
        return openSites.connected(s1.id, s2.id);
    }


    // use for unit testing (not required)
    public static void main(String[] args) {
    }

}

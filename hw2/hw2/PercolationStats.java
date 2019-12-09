package hw2;

import static java.lang.Math.pow;

import edu.princeton.cs.algs4.StdRandom;



/**
 * PercolationStats
 * Estimates the percolation threshold using the Montecarlo Method.
 */
public class PercolationStats {

    private final int N;
    private final int T;
    private final double[] thresholds;
    private final PercolationFactory pf;


    /**
     * Perform T independent experiments on an N-by-N grid.
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Grid size must be a natural number.");
        }
        this.N = N;
        this.T = T;
        this.thresholds = new double[T];
        this.pf = pf;
        simulatePercolation();
    }


    /**
     * Sample mean of percolation threshold.
     * mu = (x1 + x2 + ... + xT) / T
     */
    public double mean() {
        double sumOfThresholds = 0;
        for (int i = 0; i < T; i++) {
            sumOfThresholds += thresholds[i];
        }
        return sumOfThresholds / T;
    }

    /**
     * Sample standard deviation of percolation threshold.
     * sigma^2 = [(x1 - mu)^2 + (x2 - mu)^2 + ... + (xT - mu)^2] / (T - 1)
     */
    public double stddev() {
        double sumOfVariances = 0;
        double mean = mean();
        for (int i = 0; i < T; i++) {
            sumOfVariances += pow(thresholds[i] - mean, 2);
        }
        double sampleVariance = sumOfVariances / (T - 1);
        return pow(sampleVariance, 0.5d);
    }

    /**
     * Low endpoint of 95% confidence interval.
     * mu - (1.96 * sigma) / [T^(1/2)]
     */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / pow(T, 0.5d);
    }

    /**
     * High endpoint of 95% confidence interval.
     * mu + (1.96 * sigma) / [T^(1/2)]
     */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / pow(T, 0.5d);
    }

    private void simulatePercolation() {
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            int numOpenSites = calculateNumberOfSitesToPercolate(p);
            int totalSites = N * N;
            double threshold = ((double) numOpenSites) / totalSites;
            thresholds[i] = threshold;
        }
    }

    /**
     * Opens sites one by one until the medium percolates.
     */
    private int calculateNumberOfSitesToPercolate(Percolation p) {
        while (!p.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            p.open(row, col);
        }
        return p.numberOfOpenSites();
    }

}

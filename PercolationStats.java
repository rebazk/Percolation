import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    private int m; // Number of independent experiments.
    private double[] x; // Percolation thresholds for the m experiments.

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        // Check corner case.
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        // Initialize instance variables.
        this.m = m;
        x = new double[m];
        // Perform the following experiment m times:
        for (int i = 0; i < m; i++) {
            // Create an n × n percolation system using the UFPercolation implementation.
            UFPercolation a = new UFPercolation(n);
            // Until the system percolates, choose a site (i, j) at random and open
            // it if it is not already open.
            while (!a.percolates()) {
                int b = StdRandom.uniform(n);
                int c = StdRandom.uniform(n);
                if (!a.isOpen(b, c)) {
                    a.open(b, c);
                }
            }
            // Calculate percolation threshold as the fraction of sites opened,
            // and store the value in x[].
            x[i] = (double) a.numberOfOpenSites() / (n * n);
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return StdStats.mean(x) - ((1.96 * StdStats.stddev(x)) / Math.sqrt(m));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return StdStats.mean(x) + ((1.96 * StdStats.stddev(x)) / Math.sqrt(m));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}
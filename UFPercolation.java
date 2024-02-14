import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private int n; // Percolation system size.
    private boolean[][] open; // Percolation system.
    private int openSites; // Number of open sites.
    private WeightedQuickUnionUF uf; // Union-find representation of the percolation system.

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        // Check corner case.
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        // Initialize instance variables.
        this.n = n;
        open = new boolean[n][n];
        openSites = 0;
        uf = new WeightedQuickUnionUF((n * n) + 2);
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        // Check corner case.
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // If site (i, j) is not open:
        if (!open[i][j]) {
            // Open the site.
            open[i][j] = true;
            // Increment openSites by one.
            openSites++;
            // If the site is in the first row, connect the corresponding uf site with the source.
            if (open[0][j]) {
                uf.union(0, encode(0, j));
            }
            // If the site is in the last row, connect the corresponding uf site with the sink.
            if (open[n - 1][j]) {
                uf.union((n * n) + 1, encode(n - 1, j));
            }
            // If any of the neighbors to the north, east, west, and south of site (i, j)
            // is open, connect the uf site corresponding to site (i, j) with the uf site
            // corresponding to that neighbor.
            if ((i + 1) < n && open[i + 1][j]) {
                uf.union(encode(i, j), encode(i + 1, j));
            }
            if ((i - 1) >= 0 && open[i - 1][j]) {
                uf.union(encode(i, j), encode(i - 1, j));
            }
            if ((j + 1) < n && open[i][j + 1]) {
                uf.union(encode(i, j), encode(i, j + 1));
            }
            if ((j - 1) >= 0 && open[i][j - 1]) {
                uf.union(encode(i, j), encode(i, j - 1));
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        // Check corner case.
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Return whether site (i, j) is open or not.
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        // Check corner case.
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Return whether site (i, j) is full or not.
        return open[i][j] && uf.connected(0, encode(i, j));
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        // Return the number of open sites.
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // Return whether the system percolates or not.
        return uf.connected(0, (n * n) + 1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        // Return the UF site corresponding to the percolation system site (i, j).
        return (n * i) + j + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}
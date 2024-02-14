import stdlib.In;
import stdlib.StdOut;


// An implementation of the Percolation API using a 2D array.
public class ArrayPercolation implements Percolation {
    private int n; // size of percolation (n x n).
    private boolean[][] open; // Percolation system (true = open site, false = blocked site).
    private int openSites; // number of open sites.


    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        // Check corner case.
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        // Initialize instance variables.
        this.n = n;
        open = new boolean[n][n];
        openSites = 0;
    }


    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        // Check corner case.
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // If site (i, j) is not open:
        if (!open[i][j]) {
            // Open site (i, j).
            open[i][j] = true;
            // Increment openSites by 1.
            openSites++;
        }
    }


    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        // Check corner case.
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Return true if site (i, j) is open and false if not.
        return open[i][j];
    }


    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        // Check corner case.
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Create an n × n array of booleans called full.
        boolean[][] full = new boolean[n][n];
        // Call floodFill() on every site in the first row of the percolation system,
        // passing full as the first argument.
        for (int a = 0; a < open.length; a++) {
            floodFill(full, 0, a);
        }
        // Return full[i][j].
        return full[i][j];
    }




    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return this.openSites;
    }


    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // For each row ...
        for (int i = 0; i <= open.length - 1; i++) {
            // If the last row is full ...
            if (isFull(n - 1, i)) {
                // Then system percolates.
                return true;
            }
        }
        // If not, then system does not percolate.
        return false;
    }


    // Recursively flood fills full[][] using depth-first exploration, starting at (i, j).
    private void floodFill(boolean[][] full, int i, int j) {
        // Return if i or j is out of bounds; or site (i, j) is not open; or site (i, j) is full.
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1 || !open[i][j] || full[i][j]) {
            return;
        }
        // Fill site (i, j).
        full[i][j] = true;
        // Recursively call floodFill() on the sites to the north, east, west, and south
        // of site (i, j).
        floodFill(full, i + 1, j);
        floodFill(full, i - 1, j);
        floodFill(full, i, j + 1);
        floodFill(full, i, j - 1);
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
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

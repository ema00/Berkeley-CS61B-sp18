// Main method for running Disjoint Sets (Union Find) implemented as Weighted Quick Union.



public class Main {

    public static void main(String[] args) {
        StdOut.println("Enter the initial number of sets:");
        int n = StdIn.readInt();
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);
        do {
            StdOut.println("Enter first element to connect:");
            int p = StdIn.readInt();
            StdOut.println("Enter second element to connect:");
            int q = StdIn.readInt();
            if (set.connected(p, q)) { continue; }
            set.union(p, q);
            StdOut.println(p + " " + q);
        } while (!StdIn.isEmpty());
    }

}

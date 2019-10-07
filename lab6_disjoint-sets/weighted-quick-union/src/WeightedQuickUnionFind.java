/*
WeightedQuickUnionFind
This is an implementation of Disjoint Sets data structure (Union Find), implemented internally
as a Quick Union Find (as described in the book and courses below).

The problem is stated here:
https://sp19.datastructur.es/materials/lab/lab6/lab6

This data structure should behave just like the implementation on which specification is based:
https://algs4.cs.princeton.edu/15uf/
https://algs4.cs.princeton.edu/15uf/WeightedQuickUnionUF.java.html

This data structure is taken from the book:
Algorithms 4th Ed., by Sedgewick R. and Wayne K.
https://algs4.cs.princeton.edu/home/

And the courses:
https://algs4.cs.princeton.edu/home/
https://sp18.datastructur.es/

This implementation given here was coded by the author (me).
*/



/**
 * WeightedQuickUnionFind implementation od Disjoint Sets (Union Find).
 * @author Emanuel Aguirre
 */
public class WeightedQuickUnionFind {

    private final int n;
    private final int[] parent;
    private final int[] size;


    public WeightedQuickUnionFind(int n) {
        this.n = n;
        this.parent = new int[n];
        this.size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }


    /**
     * Validate that v1 is a valid index.
     * @throws IllegalArgumentException unless 0 <= p < n.
     */
    public void validate(int v1) {
        if (v1 < 0 || v1 >= n) {
            throw new IllegalArgumentException(v1 + " is not a valid index.");
        }
    }

    /**
     * @return the size of the set which v1 belongs to.
     */
    public int sizeOf(int v1) {
        validate(v1);
        int root = find(v1);
        return size[root];
    }

    /**
     * @return the parent node of the node v1.
     */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /**
     * @return true if v1 and v2 belong to the same set.
     */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /**
     * Performs a union operation on the sets that contain v1 and v2.
     */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);

        if (connected(v1, v2)) {
            return;
        }
        else if (sizeOf(v1) < sizeOf(v2)) {
            size[root2] += size[root1];
            size[root1] = 1;
            parent[root1] = root2;
        }
        else {
            size[root1] += size[root2];
            size[root2] = 1;
            parent[root2] = root1;
        }
    }

    /**
     * @return the root node of the set v1 belongs to.
     */
    public int find(int v1) {
        validate(v1);

        while (v1 != parent(v1)) {
            v1 = parent(v1);
        }
        return v1;
    }

}

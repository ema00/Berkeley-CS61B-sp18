import static org.junit.Assert.*;

import org.junit.Test;


public class WeightedQuickUnionFindTests {

    @Test
    public void testInitialSetSizesAreOne() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        for (int i = 0; i < n; i++) {
            assertEquals(set.sizeOf(i), 1);
        }
    }

    @Test
    public void testInitialRootOfEachIsItself() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        for (int i = 0; i < n; i++) {
            assertEquals(set.find(i), i);
        }
    }

    @Test
    public void testInitialNodesConnectedToSelf() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        for (int i = 0; i < n; i++) {
            assertTrue(set.connected(i, i));
        }
    }

    @Test
    public void testInitialSetsDisconnected() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    assertFalse(set.connected(i, j));
                }
            }
        }
    }

    @Test
    public void testUnion01() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        set.union(1, 2);
        assertEquals(set.sizeOf(1), 2);
        assertTrue(set.connected(1, 2));

        set.union(2, 3);
        assertEquals(set.sizeOf(1), 3);
        assertTrue(set.connected(1, 3));

        set.union(3, 4);
        assertEquals(set.sizeOf(1), 4);
        assertTrue(set.connected(1, 4));

        set.union(4, 5);
        assertEquals(set.sizeOf(1), 5);
        assertTrue(set.connected(1, 5));
    }

    @Test
    public void testUnion2() {
        // This tests that all the elements of a same set are connected, and
        //that all elements that were not connected are disconnected.
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        // Set size 2
        set.union(0, 1);

        // Set size 3
        set.union(2, 3);
        set.union(3, 4);

        // Union of previous sets
        set.union(0, 4);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i <= 4 && j <= 4) {
                    assertTrue(set.connected(i, j));
                }
                else if (i != j) {
                    assertFalse(set.connected(i, j));
                }
            }
        }

    }

    @Test
    public void testUnionOfSetsAlreadyConnected() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        set.union(1, 2);

        set.union(1, 2);
        assertEquals(set.sizeOf(1), 2);
        assertTrue(set.connected(1, 2));

        set.union(1, 2);
        assertEquals(set.sizeOf(1), 2);
        assertTrue(set.connected(1, 2));
    }

    @Test
    public void testBiggerSetOwnsRoot1() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        // Set of size 2
        set.union(1, 2);

        // Set of size 3
        set.union(3, 4);
        set.union(4, 5);

        // find the root of the bigger set
        int rootOfBiggerSet = set.find(4);

        // join both sets
        set.union(1, 5);

        assertEquals(set.find(2), rootOfBiggerSet);
        assertEquals(set.find(5), rootOfBiggerSet);
    }

    @Test
    public void testBiggerSetOwnsRoot2() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);

        // Set of size 2
        set.union(1, 2);

        // Set of size 3
        set.union(3, 4);
        set.union(4, 5);

        // Set of size 4
        set.union(6, 7);
        set.union(7, 8);
        set.union(8, 9);

        // join sets of size 2 and 3; this is the bigger set now because has 5 elements
        set.union(1, 3);

        // find the root of the bigger set
        int rootOfBiggerSet = set.find(1);

        // make union of sets of size 4 and 5
        set.union(1, 6);

        assertEquals(set.find(1), rootOfBiggerSet);
        assertEquals(set.find(6), rootOfBiggerSet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateSizeOf() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);
        set.sizeOf(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateParent() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);
        set.parent(n);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateConnected() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);
        set.connected(n, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateUnion() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);
        set.union(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFind() {
        int n = 15;
        WeightedQuickUnionFind set = new WeightedQuickUnionFind(n);
        set.find(n);
    }

}

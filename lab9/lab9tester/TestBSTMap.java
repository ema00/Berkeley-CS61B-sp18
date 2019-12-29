package lab9tester;

import static org.junit.Assert.*;

import org.junit.Test;
import lab9.BSTMap;

/**
 * Tests by Brendan Hu, Spring 2015, revised for 2018 by Josh Hug.
 * @author Emanuel Aguirre (tests below keySetTest(), including)
 */
public class TestBSTMap {

    @Test
    public void sanityGenericsTest() {
        try {
            BSTMap<String, String> a = new BSTMap<String, String>();
            BSTMap<String, Integer> b = new BSTMap<String, Integer>();
            BSTMap<Integer, String> c = new BSTMap<Integer, String>();
            BSTMap<Boolean, Integer> e = new BSTMap<Boolean, Integer>();
        } catch (Exception e) {
            fail();
        }
    }

    //assumes put/size/containsKey/get work
    @Test
    public void sanityClearTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1 + i);
            //make sure put is working via containsKey and get
            assertTrue(null != b.get("hi" + i));
            assertTrue(b.get("hi" + i).equals(1 + i));
            assertTrue(b.containsKey("hi" + i));
        }
        assertEquals(455, b.size());
        b.clear();
        assertEquals(0, b.size());
        for (int i = 0; i < 455; i++) {
            assertTrue(null == b.get("hi" + i) && !b.containsKey("hi" + i));
        }
    }

    // assumes put works
    @Test
    public void sanityContainsKeyTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertFalse(b.containsKey("waterYouDoingHere"));
        b.put("waterYouDoingHere", 0);
        assertTrue(b.containsKey("waterYouDoingHere"));
    }

    // assumes put works
    @Test
    public void sanityGetTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(null, b.get("starChild"));
        assertEquals(0, b.size());
        b.put("starChild", 5);
        assertTrue(((Integer) b.get("starChild")).equals(5));
        b.put("KISS", 5);
        assertTrue(((Integer) b.get("KISS")).equals(5));
        assertNotEquals(null, b.get("starChild"));
        assertEquals(2, b.size());
    }

    // assumes put works
    @Test
    public void sanitySizeTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(0, b.size());
        b.put("hi", 1);
        assertEquals(1, b.size());
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
        }
        assertEquals(456, b.size());
    }

    //assumes get/containskey work
    @Test
    public void sanityPutTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        b.put("hi", 1);
        assertTrue(b.containsKey("hi"));
        assertTrue(b.get("hi") != null);
    }

    //////////////// EVERYTHING BELOW ARE TESTS FOR THE OPTIONAL PART ////////////////

    /*
     * Test keySet() function of BSTMap.
     */
    @Test
    public void keySetTest() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();
        assertEquals(0, bm.size());
        bm.put("a", 1);
        bm.put("b", 2);
        bm.put("c", 3);
        assertEquals(3, bm.size());
        assertTrue(bm.keySet().contains("a"));
        assertTrue(bm.keySet().contains("b"));
        assertTrue(bm.keySet().contains("c"));
    }

    /*
     * Test remove(K key) function of BSTMap.
     */
    @Test
    public void removeTest() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();
        bm.put("a", 1);
        bm.put("b", 2);
        bm.put("c", 3);
        assertTrue(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
        assertTrue(bm.containsKey("c"));

        /* Remove a */
        assertEquals(new Integer(1), bm.remove("a"));
        assertFalse(bm.containsKey("a"));

        /* Rebuild same treemap */
        bm.remove("b");
        bm.remove("c");
        bm.put("a", 1);
        bm.put("b", 2);
        bm.put("c", 3);

        /* Remove b */
        assertEquals(new Integer(2), bm.remove("b"));
        assertFalse(bm.containsKey("b"));

        /* Rebuild same treemap */
        bm.remove("a");
        bm.remove("c");
        bm.put("a", 1);
        bm.put("b", 2);
        bm.put("c", 3);

        /* Remove c */
        assertEquals(new Integer(3), bm.remove("c"));
        assertFalse(bm.containsKey("c"));

        bm.remove("a");
        bm.remove("b");
        assertFalse(bm.containsKey("a") || bm.containsKey("b") || bm.containsKey("c"));
    }

    /*
     * Test remove(K key) function of BSTMap.
     */
    @Test
    public void removeHeight3Test() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();
        bm.put("c", 3);
        bm.put("a", 1);
        bm.put("b", 2);
        bm.put("d", 4);
        bm.put("e", 5);
        assertTrue(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
        assertTrue(bm.containsKey("c"));
        assertTrue(bm.containsKey("d"));
        assertTrue(bm.containsKey("e"));

        /* Remove root node */
        assertEquals(new Integer(1), bm.remove("a"));
        assertFalse(bm.containsKey("a"));
        assertTrue(bm.containsKey("e"));
        assertTrue(bm.containsKey("d"));
        assertTrue(bm.containsKey("c"));
        assertTrue(bm.containsKey("b"));

        /* Add leftmost node */
        bm.put("a", 1);

        /* Remove b */
        assertEquals(new Integer(2), bm.remove("b"));
        assertFalse(bm.containsKey("b"));

        /* Remove c */
        assertEquals(new Integer(3), bm.remove("c"));
        assertFalse(bm.containsKey("c"));

        assertFalse(bm.containsKey("b") || bm.containsKey("c"));
    }

    /*
     * Test remove(K key) function of BSTMap.
     */
    @Test
    public void removeHeight3FullTest() {
        BSTMap<String, Integer> bm = createBSTHeight3();

        /* Remove root node */
        assertEquals(new Integer(6), bm.remove("f"));

        // Check that root node (root.right) be (h, 8), before removal
        /* Remove h */
        assertEquals(new Integer(8), bm.remove("h"));

        // Check that root node (root.right) be (i, 9), before removal
        /* Remove d */
        assertEquals(new Integer(4), bm.remove("d"));

        assertTrue(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
        assertTrue(bm.containsKey("c"));
        assertTrue(bm.containsKey("e"));
        assertTrue(bm.containsKey("i"));
        assertTrue(bm.containsKey("j"));
        assertTrue(bm.containsKey("k"));
        assertTrue(bm.containsKey("l"));
    }

    @Test
    public void removeRootTest() {
        BSTMap<String, Integer> bm = createBSTHeight3();

        /* Remove root node */
        assertEquals(new Integer(6), bm.remove("f"));

        assertFalse(bm.containsKey("f"));
        assertEquals(10, bm.size());

        assertTrue(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
        assertTrue(bm.containsKey("c"));
        assertTrue(bm.containsKey("d"));
        assertTrue(bm.containsKey("e"));
        assertTrue(bm.containsKey("h"));
        assertTrue(bm.containsKey("i"));
        assertTrue(bm.containsKey("j"));
        assertTrue(bm.containsKey("k"));
        assertTrue(bm.containsKey("l"));
    }

    @Test
    public void removeFromEmptyTest() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();

        Integer b = bm.remove("b");
        Integer c = bm.remove("c");

        assertNull(b);
        assertNull(c);
        assertEquals(0, bm.size());

        bm.put("b", 2);

        b = bm.remove("b");
        assertNotNull(b);
        assertEquals(0, bm.size());

        b = bm.remove("b");
        assertNull(b);
        assertEquals(0, bm.size());
    }

    @Test
    public void removeFromRootEdges() {
        BSTMap<String, Integer> bm = createBSTHeight3();
        int initialSize = bm.size();

        // Remove left edge
        Integer c = bm.remove("c");
        assertFalse(bm.containsKey("c"));
        assertTrue(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
        assertTrue(bm.containsKey("d"));
        assertTrue(bm.containsKey("e"));
        assertTrue(bm.containsKey("h"));
        assertTrue(bm.containsKey("i"));
        assertTrue(bm.containsKey("j"));
        assertTrue(bm.containsKey("k"));
        assertTrue(bm.containsKey("l"));
        assertEquals(initialSize - 1, bm.size());


        bm = createBSTHeight3();

        // Remove right edge
        Integer j = bm.remove("j");
        assertFalse(bm.containsKey("j"));
        assertTrue(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
        assertTrue(bm.containsKey("c"));
        assertTrue(bm.containsKey("d"));
        assertTrue(bm.containsKey("e"));
        assertTrue(bm.containsKey("h"));
        assertTrue(bm.containsKey("i"));
        assertTrue(bm.containsKey("k"));
        assertTrue(bm.containsKey("l"));
        assertEquals(initialSize - 1, bm.size());
    }

    /* This is becaus of how it is implemented: when removing, it looks for the replacement
    * as the smallest element of the right subtree of the removed node.
    */
    @Test
    public void removeFromRootWithRightLeaf() {
        BSTMap<String, Integer> bm = createBSTHeight3();
        int initialSize = bm.size();

        // Delete all on the right but the edge (leave a leaf as right child of root)
        bm.remove("h");
        bm.remove("i");
        bm.remove("k");
        bm.remove("l");

        bm.remove("f");
        // "j" should be the new root
        assertTrue(bm.containsKey("j"));
        assertEquals(initialSize - 5, bm.size());
        // all keys on the left remain in the tree
        assertTrue(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
        assertTrue(bm.containsKey("c"));
        assertTrue(bm.containsKey("d"));
        assertTrue(bm.containsKey("e"));
    }

    @Test
    public void removeFromTreeLinkedListRight() {
        BSTMap<String, Integer> bm = createBSTHeight5LinkedListRight();

        assertEquals(5, bm.size());

        // Remove all the ones that are root
        bm.remove("a");
        assertFalse(bm.containsKey("a"));
        bm.remove("b");
        assertFalse(bm.containsKey("b"));
        bm.remove("c");
        assertFalse(bm.containsKey("c"));
        bm.remove("d");
        assertFalse(bm.containsKey("d"));
        bm.remove("e");
        assertFalse(bm.containsKey("e"));

        assertEquals(0, bm.size());
    }

    @Test
    public void removeFromTreeLinkedListLeft() {
        BSTMap<String, Integer> bm = createBSTHeight5LinkedListLeft();

        assertEquals(5, bm.size());

        // Remove all the ones that are root
        bm.remove("e");
        assertFalse(bm.containsKey("e"));
        bm.remove("d");
        assertFalse(bm.containsKey("d"));
        bm.remove("c");
        assertFalse(bm.containsKey("c"));
        bm.remove("b");
        assertFalse(bm.containsKey("b"));
        bm.remove("a");
        assertFalse(bm.containsKey("a"));

        assertEquals(0, bm.size());
    }

    /*
     * Test remove(K key, V value) function of BSTMap.
     */
    @Test
    public void removeIfMappedTest() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();
        bm.put("a", 1);
        bm.put("b", 2);

        /* a should be removed, but b should not be removed because it is not mapped to 1. */
        assertEquals(new Integer(1), bm.remove("a", 1));
        assertNull(bm.remove("b", 1));

        assertFalse(bm.containsKey("a"));
        assertTrue(bm.containsKey("b"));
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestBSTMap.class);
    }

    /* Helper methods. */

    private BSTMap<String, Integer> createBSTHeight3() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();

        bm.put("f", 6);         // root

        bm.put("c", 3);         // all these are at left of root
        bm.put("e", 5);         // rightmost
        bm.put("d", 4);         // left of rightmost
        bm.put("a", 1);
        bm.put("b", 2);

        bm.put("j", 10);        // all these are at right of root
        bm.put("h", 8);         // leftmost
        bm.put("i", 9);         // right of leftmost
        bm.put("k", 11);
        bm.put("l", 12);

        return bm;
    }

    private BSTMap<String, Integer> createBSTHeight5LinkedListRight() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();
        bm.put("a", 1);
        bm.put("b", 2);
        bm.put("c", 3);
        bm.put("d", 4);
        bm.put("e", 5);
        return bm;
    }

    private BSTMap<String, Integer> createBSTHeight5LinkedListLeft() {
        BSTMap<String, Integer> bm = new BSTMap<String, Integer>();
        bm.put("e", 5);
        bm.put("d", 4);
        bm.put("c", 3);
        bm.put("b", 2);
        bm.put("a", 1);
        return bm;
    }

}

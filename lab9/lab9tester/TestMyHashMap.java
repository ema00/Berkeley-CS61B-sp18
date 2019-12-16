package lab9tester;

import static org.junit.Assert.*;

import org.junit.Test;
import lab9.MyHashMap;

/**
 * Tests by Brendan Hu, Spring 2015, revised for 2018 by Josh Hug
 */
public class TestMyHashMap {

    @Test
    public void sanityGenericsTest() {
        try {
            MyHashMap<String, String> a = new MyHashMap<String, String>();
            MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
            MyHashMap<Integer, String> c = new MyHashMap<Integer, String>();
            MyHashMap<Boolean, Integer> e = new MyHashMap<Boolean, Integer>();
        } catch (Exception e) {
            fail();
        }
    }

    //assumes put/size/containsKey/get work
    @Test
    public void sanityClearTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
            //make sure put is working via containsKey and get
            assertTrue(null != b.get("hi" + i)
                    && b.containsKey("hi" + i));
        }
        b.clear();
        assertEquals(0, b.size());
        for (int i = 0; i < 455; i++) {
            assertTrue(null == b.get("hi" + i) && !b.containsKey("hi" + i));
        }
    }

    // assumes put works
    @Test
    public void sanityContainsKeyTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        assertFalse(b.containsKey("waterYouDoingHere"));
        b.put("waterYouDoingHere", 0);
        assertTrue(b.containsKey("waterYouDoingHere"));
    }

    // assumes put works
    @Test
    public void sanityGetTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        assertEquals(null, b.get("starChild"));
        b.put("starChild", 5);
        assertNotEquals(null, b.get("starChild"));
        b.put("KISS", 5);
        assertNotEquals(null, b.get("KISS"));
        assertNotEquals(null, b.get("starChild"));
    }

    // assumes put works
    @Test
    public void sanitySizeTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
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
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        b.put("hi", 1);
        assertTrue(b.containsKey("hi") && b.get("hi") != null);
    }

    /*
     * Test for general functionality and that the properties of Maps hold.
     */
    @Test
    public void functionalityTest() {
        MyHashMap<String, String> dictionary = new MyHashMap<>();
        assertEquals(0, dictionary.size());

        // can put objects in dictionary and get them
        dictionary.put("hello", "world");
        assertTrue(dictionary.containsKey("hello"));
        assertEquals("world", dictionary.get("hello"));
        assertEquals(1, dictionary.size());

        // putting with existing key updates the value
        dictionary.put("hello", "kevin");
        assertEquals(1, dictionary.size());
        assertEquals("kevin", dictionary.get("hello"));

        // putting key in multiple times does not affect behavior
        MyHashMap<String, Integer> studentIDs = new MyHashMap<>();
        studentIDs.put("sarah", 12345);
        assertEquals(1, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        studentIDs.put("alan", 345);
        assertEquals(2, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        assertEquals(345, studentIDs.get("alan").intValue());
        studentIDs.put("alan", 345);
        assertEquals(2, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        assertEquals(345, studentIDs.get("alan").intValue());
        studentIDs.put("alan", 345);
        assertEquals(2, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        assertEquals(345, studentIDs.get("alan").intValue());
        assertTrue(studentIDs.containsKey("sarah"));
        assertTrue(studentIDs.containsKey("alan"));

        // handle values being the same
        assertEquals(345, studentIDs.get("alan").intValue());
        studentIDs.put("evil alan", 345);
        assertEquals(345, studentIDs.get("evil alan").intValue());
        assertEquals(studentIDs.get("evil alan"), studentIDs.get("alan"));
    }

    //////////////// EVERYTHING BELOW ARE TESTS FOR THE OPTIONAL PART ////////////////

    /*
     * Test keySet() function of MyHashMap.
     */
    @Test
    public void keySetTest() {
        MyHashMap<String, Integer> hm = new MyHashMap<String, Integer>();
        assertEquals(0, hm.size());
        hm.put("a", 1);
        hm.put("b", 2);
        hm.put("c", 3);
        assertEquals(3, hm.size());
        assertTrue(hm.keySet().contains("a"));
        assertTrue(hm.keySet().contains("b"));
        assertTrue(hm.keySet().contains("c"));
    }

    /*
     * Test remove(K key) function of MyHashMap.
     */
    @Test
    public void removeTest() {
        MyHashMap<String, Integer> hm = new MyHashMap<String, Integer>();
        hm.put("a", 1);
        hm.put("b", 2);
        hm.put("c", 3);
        assertTrue(hm.containsKey("a"));
        assertTrue(hm.containsKey("b"));
        assertTrue(hm.containsKey("c"));

        /* Remove a */
        assertEquals(new Integer(1), hm.remove("a"));
        assertFalse(hm.containsKey("a"));

        /* Rebuild same hashmap */
        hm.remove("b");
        hm.remove("c");
        hm.put("a", 1);
        hm.put("b", 2);
        hm.put("c", 3);

        /* Remove b */
        assertEquals(new Integer(2), hm.remove("b"));
        assertFalse(hm.containsKey("b"));

        /* Rebuild same hashmap */
        hm.remove("a");
        hm.remove("c");
        hm.put("a", 1);
        hm.put("b", 2);
        hm.put("c", 3);

        /* Remove c */
        assertEquals(new Integer(3), hm.remove("c"));
        assertFalse(hm.containsKey("c"));

        hm.remove("a");
        hm.remove("b");
        assertFalse(hm.containsKey("a") || hm.containsKey("b") || hm.containsKey("c"));
    }

    /*
     * Test remove(K key) function of MyHashMap.
     */
    @Test
    public void removeIfMappedTest() {
        MyHashMap<String, Integer> hm = new MyHashMap<String, Integer>();
        hm.put("a", 1);
        hm.put("b", 2);

        /* a should be removed, but b should not be removed because it is not mapped to 1. */
        assertEquals(new Integer(1), hm.remove("a", 1));
        assertNull(hm.remove("b", 1));

        assertFalse(hm.containsKey("a"));
        assertTrue(hm.containsKey("b"));
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestMyHashMap.class);
    }
}

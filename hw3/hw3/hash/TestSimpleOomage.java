package hw3.hash;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;



public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        final int colorDepth = 255;
        final int colorMultiple = 5;
        /* Number of possible valid values for each color. */
        final int numValuesPerColor = colorDepth / colorMultiple + 1;
        /* Number of hash codes for all possible combinations of colors. */
        final int numDifferentHashes = numValuesPerColor * numValuesPerColor * numValuesPerColor;
        /* Set that will contain all the hash codes to check if there are repeated ones. */
        Set<Integer> hashCodes = new TreeSet<>();

        for (int r = 0; r <= colorDepth; r += colorMultiple) {
            for (int g = 0; g <= colorDepth; g += colorMultiple) {
                for (int b = 0; b <= colorDepth; b += colorMultiple) {
                    Oomage oomage = new SimpleOomage(r, g, b);
                    int hashCode = oomage.hashCode();
                    hashCodes.add(hashCode);
                }
            }
        }

        assertEquals(numDifferentHashes, hashCodes.size());
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    /* TODO: Uncomment this test after you finish haveNiceHashCode Spread in OomageTestUtility */
    /*@Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }*/

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }

}

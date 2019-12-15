package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }


    @Test
    public void testWithDeadlyParams() {
        /*
        M is the number of buckets that are used to test the hash code (as if were in a HashSet).
        N is the number of elements to (supposedly) add to the (hypothetical) HasSet.
        According to OomageTestUtility, it fails if any bucket Bx has: Bx < N/50 or Bx > N/2.5.
        So if N = 40, Bx >= 0 and Bx < 16.
        If uniformly distributed (as random would do), each bucket can get 40/10 = 4.
        So, with at least 16 non random elements in the deadly list, the Hash Code should fail,
        because of Bx > N/2.5 (Bx > 40/2.5 => Bx > 16).
        */
        List<Oomage> deadlyList = new ArrayList<>();

        /* Apart from the hint, another way to fail is to make all the hash codes fall in the same
        bucket, in this case: 0. For that, every hash code has to be a multiple of 10. */
        Integer[] paramArr00 = new Integer[]{255, 255, 255, 255};
        Integer[] paramArr01 = new Integer[]{10, 255, 255, 255, 255};
        Integer[] paramArr02 = new Integer[]{5, 255, 255, 255, 255};
        Integer[] paramArr03 = new Integer[]{0, 255, 255, 255, 255};
        Integer[] paramArr04 = new Integer[]{1, 255, 255, 255, 255};
        Integer[] paramArr05 = new Integer[]{50, 255, 255, 255, 255};
        Integer[] paramArr06 = new Integer[]{10, 0, 255, 255, 255, 255};
        Integer[] paramArr07 = new Integer[]{10, 3, 255, 255, 255, 255};
        Integer[] paramArr08 = new Integer[]{7, 255, 255, 255, 255};
        Integer[] paramArr09 = new Integer[]{10, 4, 255, 255, 255, 255};
        Integer[] paramArr10 = new Integer[]{100, 255, 255, 255, 255};
        Integer[] paramArr11 = new Integer[]{150, 255, 255, 255, 255};
        Integer[] paramArr12 = new Integer[]{160, 255, 255, 255, 255};
        Integer[] paramArr13 = new Integer[]{170, 255, 255, 255, 255};
        Integer[] paramArr14 = new Integer[]{200, 255, 255, 255, 255};
        Integer[] paramArr15 = new Integer[]{7, 255, 255, 255, 255};

        List<List<Integer>> paramsList = new ArrayList<>();
        paramsList.add(Arrays.asList(paramArr00));
        paramsList.add(Arrays.asList(paramArr01));
        paramsList.add(Arrays.asList(paramArr02));
        paramsList.add(Arrays.asList(paramArr03));
        paramsList.add(Arrays.asList(paramArr04));
        paramsList.add(Arrays.asList(paramArr05));
        paramsList.add(Arrays.asList(paramArr06));
        paramsList.add(Arrays.asList(paramArr07));
        paramsList.add(Arrays.asList(paramArr08));
        paramsList.add(Arrays.asList(paramArr09));
        paramsList.add(Arrays.asList(paramArr10));
        paramsList.add(Arrays.asList(paramArr11));
        paramsList.add(Arrays.asList(paramArr12));
        paramsList.add(Arrays.asList(paramArr13));
        paramsList.add(Arrays.asList(paramArr14));
        paramsList.add(Arrays.asList(paramArr15));

        for (List<Integer> params : paramsList) {
            deadlyList.add(new ComplexOomage(params));
        }
        for (int i = 0; i < 23; i++) {
            deadlyList.add(ComplexOomage.randomComplexOomage());
        }
        for (Oomage o : deadlyList) {
            System.out.println(o.hashCode() % 10);
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }

}

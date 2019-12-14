package hw3.hash;

import java.util.List;



public class OomageTestUtility {

    /* Bit mask to convert an int to an int with a positive sign safely.
    Math.abs throws an error if the value to convert is the greatest negative int -2147483648. */
    static final int INT_TO_UNSIGNED_BIT_MASK = 0x7FFFFFFF;

    /**
     * Utility function that returns true if the given oomages
     * have hashCodes that would distribute them fairly evenly across
     * M buckets. To do this, convert each oomage's hashcode in the
     * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
     * and ensure that no bucket has fewer than N / 50
     * Oomages and no bucket has more than N / 2.5 Oomages.
     */
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        final int N = oomages.size();
        final double lowerBound = N / 50;
        final double upperBound = N / 2.5;
        final int[] oomagesPerBucket = new int[M];

        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & INT_TO_UNSIGNED_BIT_MASK) % M;
            oomagesPerBucket[bucketNum] += 1;
        }
        for (int i = 0; i < oomagesPerBucket.length; i++) {
            if (oomagesPerBucket[i] <= lowerBound || oomagesPerBucket[i] >= upperBound) {
                return false;
            }
        }
        return true;
    }

}

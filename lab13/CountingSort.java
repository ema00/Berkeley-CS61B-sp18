


/**
 * CS61B, Lab. 13: https://sp18.datastructur.es/materials/lab/lab13/lab13
 *
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 * @author Emanuel Aguirre (implementation of betterCountingSort)
 **/
public class CountingSort {

    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        // find min and max, and count the number of negative and non-negative
        int minNeg = 0;
        int maxNonNeg = 0;
        int numNeg = 0;
        int numNonNeg = 0;
        for (int i : arr) {
            minNeg = i < minNeg ? i : minNeg;
            maxNonNeg = i > maxNonNeg ? i : maxNonNeg;
            numNeg = i < 0 ? numNeg + 1 : numNeg;
            numNonNeg = i >= 0 ? numNonNeg + 1 : numNonNeg;
        }
        // create array for counting the number of occurrences of negative values
        // note: the array that counts occurrences of negative, wastes the 0 index position
        int[] countNeg = minNeg != 0 ? new int[-minNeg + 1] : new int[0];
        // create array for counting the number of occurrences of positive values and zero
        int[] countNonNeg = new int[maxNonNeg + 1];
        // count negative and non-negative occurrences
        for (int i : arr) {
            if (i >= 0) {
                countNonNeg[i] += 1;
            } else {
                countNeg[-i] += 1;
            }
        }
        // calculate the start position of each number, based on the number of repetitions
        int[] startNeg = minNeg != 0 ? new int[-minNeg + 1] : new int[0];
        int posNeg = startNeg.length > 0 ? 1 : 0;       // start at 1
        for (int i = 1; i < countNeg.length; i++) {
            startNeg[i] = posNeg;
            posNeg += countNeg[i];
        }
        int[] startNonNeg = new int[maxNonNeg + 1];
        int posNonNeg = 0;                              // start at 0
        for (int i = 0; i < countNonNeg.length; i++) {
            startNonNeg[i] = posNonNeg;
            posNonNeg += countNonNeg[i];
        }
        // copy values in original array to the result negative and non-negative sorted arrays
        int[] sortedNeg = new int[numNeg];
        int[] sortedNonNeg = new int[numNonNeg];
        for (int i : arr) {
            if (i < 0) {
                int position = startNeg[-i] - 1;
                sortedNeg[position] = i;
                startNeg[-i] += 1;
            } else {
                int position = startNonNeg[i];
                sortedNonNeg[position] = i;
                startNonNeg[i] += 1;
            }
        }
        // copy the negative and non-negative sorted arrays to the result array
        int[] result = new int[arr.length];
        int ri = 0;
        for (int i = sortedNeg.length - 1; i >= 0; i--) {
            result[ri] = sortedNeg[i];
            ri += 1;
        }
        for (int i = 0; i < sortedNonNeg.length; i++) {
            result[ri] = sortedNonNeg[i];
            ri += 1;
        }
        return result;
    }

}

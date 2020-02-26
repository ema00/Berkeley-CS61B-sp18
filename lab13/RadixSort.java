import java.util.Arrays;



/**
 * CS61B, Lab. 13: https://sp18.datastructur.es/materials/lab/lab13/lab13
 *
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 * @author Emanuel Aguirre (implementation of sortHelperLSD, radixSortOnPosition and maxLength)
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * Implementation note: performs an LSD radix sort on a stream; to accomplish it, makes all
     * strings be the same length by adding (to the shorter ones) a character lesser than the
     * others on the right; then sorts using radix sort on each character in an LSD (right to
     * left) fashion. For being able to add a character lesser than the others, strings are pre-
     * viously converted to int arrays, all the characters are increased by 1, and 0 is used as
     * the character lesser than the others; after sorting, all strings are removed the added
     * characters and returned back to being strings.
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int max = maxLength(asciis);
        int[][] unsorted = new int[asciis.length][];
        for (int i = 0; i < unsorted.length; i++) {
            unsorted[i] = convertToIntArrayOfLength(asciis[i], max);
        }

        int[][] preRadixSort = unsorted;
        int[][] posRadixSort = null;
        for (int pos = max - 1; pos >= 0; pos--) {
            posRadixSort = radixSortOnPosition(preRadixSort, pos);
            preRadixSort = posRadixSort;
        }

        String[] sorted = new String[asciis.length];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = convertFromIntArray(posRadixSort[i]);
        }
        return sorted;
    }

    /**
     * Auxiliary method that sorts and array of arrays of int, by the ints at one particular
     * position of the arrays of ints. All the arrays of ints must be of the same length.
     * This preforms counting sort, and is called repeatedly to lexicographically order a String.
     * Since this is used to order Strings lexicographically, the arrays are completed with 0
     * to the right, and that is why one mora place is added to the arrays cof counts and starts.
     */
    private static int[][] radixSortOnPosition(int[][] strings, int pos) {
        char smallest = 0;
        char largest = 255;
        int diffChars = largest - smallest + 2;     // all chars are shifted +1, and 0 is for blank
        // Count how many times each different char appears at the position (pos) in each string.
        int[] counts = new int[diffChars];
        for (int i = 0; i < strings.length; i++) {
            int charPosition = strings[i][pos];
            counts[charPosition] += 1;
        }
        // Calculate the position in the result array on which each radix starts.
        int[] starts = new int[diffChars];
        int radixPosition = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = radixPosition;
            radixPosition += counts[i];
        }
        // Sort each string, from the initial array into result, by the character at the position.
        int[][] result = new int[strings.length][strings[0].length];
        for (int i = 0; i < strings.length; i++) {
            int charAtPos = strings[i][pos];
            int stringPosition = starts[charAtPos];
            result[stringPosition] = strings[i];
            starts[charAtPos] += 1;
        }
        return result;
    }

    /**
     * Increments every char by 1, and converts the char array to an String.
     * Fills the array to the right with 0s to complete the required length of the array.
     */
    private static int[] convertToIntArrayOfLength(String string, int length) {
        int[] result = new int[length];
        int[] intString = string.chars().toArray();
        // increment every char by 1
        for (int i = 0; i < intString.length; i++) {
            intString[i] = intString[i] + 1;
        }
        result = Arrays.copyOfRange(intString, 0, result.length);
        return result;
    }

    /**
     * Decrements every char by 1, and converts the int array to a String.
     */
    private static String convertFromIntArray(int[] intChars) {
        String result = "";
        // decrement every char by 1, and concatenate into a String
        for (int i = 0; i < intChars.length; i++) {
            if (intChars[i] == 0) {
                break;
            }
            result += (char) (intChars[i] - 1);
        }
        return result;
    }

    /**
     * Returns the length of the longest String of an array of String.
     */
    private static int maxLength(String[] asciis) {
        int max = 0;
        for (String string : asciis) {
            max = string.length() > max ? string.length() : max;
        }
        return max;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }


    // Main method to test sort
    public static void main(String[] args) {
        String[] unsorted0 = {"blala", "asdasc", "fdsd", "eef", "e", "aaaaaaaaaaa"};
        String[] unsorted1 = {"b", "c", "f", "e", "d", "a"};
        String[] unsorted2 = {"ba", "ca", "fa", "ea", "da", "aa"};
        String[] unsorted3 = {"ab", "ac", "af", "ae", "ad", "aa"};
        String[] unsorted4 = {"abc", "ab", "aba", "ae", "ac", "aa"};
        String[] unsorted5 = {"ab", "abc", "aba", "eef", "ee", "ac", "aa"};
        String[] sorted0 = sort(unsorted0);
        String[] sorted1 = sort(unsorted1);
        String[] sorted2 = sort(unsorted2);
        String[] sorted3 = sort(unsorted3);
        String[] sorted4 = sort(unsorted4);
        String[] sorted5 = sort(unsorted5);
        System.out.println(Arrays.asList(sorted0));
        System.out.println(Arrays.asList(sorted1));
        System.out.println(Arrays.asList(sorted2));
        System.out.println(Arrays.asList(sorted3));
        System.out.println(Arrays.asList(sorted4));
        System.out.println(Arrays.asList(sorted5));
    }

}

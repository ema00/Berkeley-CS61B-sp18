/**
 * OffByOne
 * Compares characters for a difference of exactly one.
 */

public class OffByOne implements CharacterComparator {

    @Override
    /**
     * Returns true for characters that are different by exactly one.
     */
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }

}

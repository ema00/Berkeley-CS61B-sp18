/**
 * OffByOne
 * Compares characters for a difference of exactly N.
 */

public class OffByN implements CharacterComparator {

    private int N;


    public OffByN(int N) {
        this.N = N;
    }


    /**
     * Returns true for characters that are different by exactly one.
     */
    @Override
    public boolean equalChars(char x, char y) {
        if (('a' <= x && x <= 'z') && (('a' <= y && y <= 'z'))) {
            return Math.abs(x - y) == N;
        }
        if (('A' <= x && x <= 'Z') && (('A' <= y && y <= 'Z'))) {
            return Math.abs(x - y) == N;
        }
        return Math.abs(x - y) == N;
    }
}

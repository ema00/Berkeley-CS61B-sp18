


/**
 * OffByOne
 * Compares characters for a difference of exactly one.
 * @author Emanuel Aguirre
 */
public class OffByOne implements CharacterComparator {

    /**
     * Returns true for characters that are different by exactly one.
     */
    @Override
    public boolean equalChars(char x, char y) {
        if (('a' <= x && x <= 'z') && (('a' <= y && y <= 'z'))) {
            return Math.abs(x - y) == 1;
        }
        if (('A' <= x && x <= 'Z') && (('A' <= y && y <= 'Z'))) {
            return Math.abs(x - y) == 1;
        }
        return Math.abs(x - y) == 1;
    }

}

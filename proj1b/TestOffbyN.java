import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffbyN {

    private static final int N = 5;
    static CharacterComparator offByN = new OffByN(N);

    @Test
    public void testEqualCharsDifferentCharsLowercase1() {
        char x = 'm';
        char y = (char) (x + N + 2);
        char z = (char) (x + N - 2);
        assertFalse(offByN.equalChars(x, y));
        assertFalse(offByN.equalChars(x, z));
    }

    @Test
    public void testEqualCharsDifferentCharsLowercase2() {
        char x = 'm';
        char y = (char) (x - N + 2);
        char z = (char) (x - N - 2);
        assertFalse(offByN.equalChars(x, y));
        assertFalse(offByN.equalChars(x, z));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase1() {
        char x = 'M';
        char y = (char) (x + N + 2);
        char z = (char) (x + N - 2);
        assertFalse(offByN.equalChars(x, y));
        assertFalse(offByN.equalChars(x, z));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase2() {
        char x = 'M';
        char y = (char) (x - N + 2);
        char z = (char) (x - N - 2);
        assertFalse(offByN.equalChars(x, y));
        assertFalse(offByN.equalChars(x, z));
    }

    @Test
    public void testEqualCharsEqualCharsLowercase() {
        char x = 'm';
        char y = (char) (x + N);
        char z = (char) (x - N);
        assertTrue(offByN.equalChars(x, y));
        assertTrue(offByN.equalChars(x, z));
    }

    @Test
    public void testEqualCharsEqualCharsUppercase() {
        char x = 'M';
        char y = (char) (x + N);
        char z = (char) (x - N);
        assertTrue(offByN.equalChars(x, y));
        assertTrue(offByN.equalChars(x, z));
    }

    @Test
    public void testEqualCharsDifferentCase() {
        char v = 'm';
        char w = 'M';
        char x = (char) (w + N);
        char y = (char) (v + N);
        assertFalse(offByN.equalChars(v, x));
        assertFalse(offByN.equalChars(w, y));
        assertFalse(offByN.equalChars(x, v));
        assertFalse(offByN.equalChars(y, w));
    }

    @Test
    public void testEqualCharsEqualNonLetters() {
        char x = '(';
        char y = (char) (x - N);
        char z = (char) (x + N);
        assertTrue(offByN.equalChars(x, y));
        assertTrue(offByN.equalChars(x, z));
    }

    @Test
    public void testEqualCharsDifferentNonLetters() {
        char x = '%';
        char y = (char) (x  + N - 2);
        char z = (char) (x  + N + 2);
        assertFalse(offByN.equalChars(x, y));
        assertFalse(offByN.equalChars(x, z));
    }

}

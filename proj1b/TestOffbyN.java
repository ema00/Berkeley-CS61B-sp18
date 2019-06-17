import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffbyN {

    private static final int N = 5;
    static CharacterComparator offByN = new OffByN(N);

    @Test
    public void testEqualCharsDifferentCharsLowercase1() {
        char x = 'm';
        char y = (char) (x + N + 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsLowercase2() {
        char x = 'm';
        char y = (char) (x + N - 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsLowercase3() {
        char x = 'm';
        char y = (char) (x - N + 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsLowercase4() {
        char x = 'm';
        char y = (char) (x - N - 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase1() {
        char x = 'M';
        char y = (char) (x + N + 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase2() {
        char x = 'M';
        char y = (char) (x + N - 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase3() {
        char x = 'M';
        char y = (char) (x - N + 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase4() {
        char x = 'M';
        char y = (char) (x - N - 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsLowercase1() {
        char x = 'm';
        char y = (char) (x + N);
        assertTrue(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsLowercase2() {
        char x = 'm';
        char y = (char) (x - N);
        assertTrue(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsUppercase1() {
        char x = 'M';
        char y = (char) (x + N);
        assertTrue(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsUppercase2() {
        char x = 'M';
        char y = (char) (x - N);
        assertTrue(offByN.equalChars(x, y));
    }

}

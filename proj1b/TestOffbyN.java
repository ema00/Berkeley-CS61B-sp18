import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffbyN {

    private static final int N = 5;
    static CharacterComparator offByN = new OffByN(N);

    @Test
    public void testEqualCharsDifferentChars1() {
        char x = 'a';
        char y = (char) (x + N + 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentChars2() {
        char x = 'a';
        char y = (char) (x + N - 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentChars3() {
        char x = 'a';
        char y = (char) (x - N + 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentChars4() {
        char x = 'a';
        char y = (char) (x - N - 2);
        assertFalse(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsLowercase1() {
        char x = 'a';
        char y = (char) (x + N);
        assertTrue(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsLowercase2() {
        char x = 'z';
        char y = (char) (x - N);
        assertTrue(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsUppercase1() {
        char x = 'A';
        char y = (char) (x + N);
        assertTrue(offByN.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsUppercase2() {
        char x = 'Z';
        char y = (char) (x - N);
        assertTrue(offByN.equalChars(x, y));
    }

}

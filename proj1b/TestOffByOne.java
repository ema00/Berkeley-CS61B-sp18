import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualCharsSameCharsLowercase() {
        char x = 'c';
        char y = x;
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsSameCharsUppercase() {
        char x = 'C';
        char y = x;
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsLowercase1() {
        char x = 'c';
        char y = (char) (x + 2);
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsLowercase2() {
        char x = 'c';
        char y = (char) (x - 2);
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase1() {
        char x = 'C';
        char y = (char) (x + 2);
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentCharsUppercase2() {
        char x = 'C';
        char y = (char) (x - 2);
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsEqualCharsLowercase1() {
        char x = 'c';
        char y = (char) (x + 1);
        assertTrue(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsEqualCharsLowercase2() {
        char x = 'c';
        char y = (char) (x - 1);
        assertTrue(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsEqualCharsUppercase1() {
        char x = 'C';
        char y = (char) (x + 1);
        assertTrue(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsEqualCharsUppercase2() {
        char x = 'C';
        char y = (char) (x - 1);
        assertTrue(offByOne.equalChars(x, y));
    }

}
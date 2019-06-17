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
    public void testEqualCharsDifferentCase() {
        char v = 'c';
        char w = 'C';
        char x = 'D';
        char y = 'd';
        assertFalse(offByOne.equalChars(v, x));
        assertFalse(offByOne.equalChars(w, y));
        assertFalse(offByOne.equalChars(x, v));
        assertFalse(offByOne.equalChars(y, w));
    }

    @Test
    public void testEqualCharsEqualNonLetters() {
        char x = '%';
        char y = (char) (x - 1);
        char z = (char) (x + 1);
        assertTrue(offByOne.equalChars(x, y));
        assertTrue(offByOne.equalChars(x, z));
    }

    @Test
    public void testEqualCharsDifferentNonLetters() {
        char x = '%';
        char y = (char) (x - 2);
        char z = (char) (x + 2);
        assertFalse(offByOne.equalChars(x, y));
        assertFalse(offByOne.equalChars(x, z));
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

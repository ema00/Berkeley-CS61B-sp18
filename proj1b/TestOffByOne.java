import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualCharsSameChars() {
        char x = 'a';
        char y = 'a';
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentChars1() {
        char x = 'a';
        char y = (char) (x + 2);
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsDifferentChars2() {
        char x = 'a';
        char y = (char) (x - 2);
        assertFalse(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsEqualChars1() {
        char x = 'a';
        char y = (char) (x + 1);
        assertTrue(offByOne.equalChars(x, y));
    }

    @Test
    public void testEqualCharsEqualChars2() {
        char x = 'a';
        char y = (char) (x - 1);
        assertTrue(offByOne.equalChars(x, y));
    }

}

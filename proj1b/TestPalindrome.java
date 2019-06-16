import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    /* These test Palindrome.isPalindrome(String word) */

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindromeLengthZero() {
        String wordOfLengthZero = "";
        assertTrue(palindrome.isPalindrome(wordOfLengthZero));
    }

    @Test
    public void testIsPalindromeLengthOne() {
        String wordOfLengthOne = "a";
        assertTrue(palindrome.isPalindrome(wordOfLengthOne));
    }

    @Test
    public void testIsPalindromeIsPalindrome() {
        String wordIsPalindrome = "racecar";
        assertTrue(palindrome.isPalindrome(wordIsPalindrome));
    }

    @Test
    public void testIsPalindromeIsNotPalindrome1() {
        String wordIsNotPalindrome = "house";
        assertFalse(palindrome.isPalindrome(wordIsNotPalindrome));
    }

    @Test
    public void testIsPalindromeIsNotPalindrome2() {
        String wordIsNotPalindrome = "Racecar";
        assertFalse(palindrome.isPalindrome(wordIsNotPalindrome));
    }


    /* These test Palindrome.isPalindrome(String word, CharacterComparator cc) */

    static CharacterComparator cc = new OffByOne();

    // TODO
    @Test
    public void testIsPalindromeCCLengthZero() {
        String wordOfLengthZero = "";
        assertTrue(palindrome.isPalindrome(wordOfLengthZero, cc));
    }

    // TODO
    @Test
    public void testIsPalindromeCCLengthOne() {
        String wordOfLengthOne = "a";
        assertTrue(palindrome.isPalindrome(wordOfLengthOne, cc));
    }

    // TODO
    @Test
    public void testIsPalindromeCCIsPalindrome() {
        String wordIsPalindrome = "racecar";
        assertTrue(palindrome.isPalindrome(wordIsPalindrome, cc));
    }

    // TODO
    @Test
    public void testIsPalindromeCCIsNotPalindrome1() {
        String wordIsNotPalindrome = "house";
        assertFalse(palindrome.isPalindrome(wordIsNotPalindrome, cc));
    }

    // TODO
    @Test
    public void testIsPalindromeCCIsNotPalindrome2() {
        String wordIsNotPalindrome = "Racecar";
        assertFalse(palindrome.isPalindrome(wordIsNotPalindrome, cc));
    }

}

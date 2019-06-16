/**
 * Palindrome
 */

public class Palindrome {

    /**
     * Converts a String to a Deque.
     * @param word the String to be converted.
     * @return a Deque (double ended queue) containing the caracters of the String word.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (Character c : word.toCharArray()) {
            deque.addLast(c);
        }
        return deque;
    }

    /**
     * Checks if the String passed as parameter is a palindrome.
     * @param word is the String to be checked for being or not a palindrome.
     * @return true or false whether the String passed as parameter is a palindrome or not.
     */
    public boolean isPalindrome(String word) {
        return false;
    }

}

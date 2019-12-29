


/**
 * Palindrome
 * @author Emanuel Aguirre
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
        Deque<Character> chars = wordToDeque(word);
        return isPalindrome(chars);
    }

    /**
     * Checks if the String passed as parameter is a palindrome.
     * @param word is the String to be checked for being or not a palindrome.
     * @param cc is an instance of CharacterComparator used for comparing equality of characters.
     * @return true or false whether the String passed as parameter is a palindrome or not.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> chars = wordToDeque(word);
        return isPalindrome(chars, cc);
    }

    /**
     * Checks if the the characters in the deque passed as parameter form a palindrome.
     * @param chars is a Deque, representing a word, to be checked for being or not a palindrome.
     * @return true or false whether the characters in the deque form a palindrome or not.
     */
    private boolean isPalindrome(Deque<Character> chars) {
        if (chars.size() == 1 || chars.size() == 0) {
            return true;
        }
        Character first = chars.removeFirst();
        Character last = chars.removeLast();
        return first.equals(last) && isPalindrome(chars);
    }

    /**
     * Checks if the the characters in the deque passed as parameter form a palindrome.
     * @param chars is a Deque, representing a word, to be checked for being or not a palindrome.
     * @param cc is an instance of CharacterComparator used for comparing equality of characters.
     * @return true or false whether the characters in the deque form a palindrome or not.
     */
    private boolean isPalindrome(Deque<Character> chars, CharacterComparator cc) {
        if (chars.size() == 1 || chars.size() == 0) {
            return true;
        }
        Character first = chars.removeFirst();
        Character last = chars.removeLast();
        return cc.equalChars(first, last) && isPalindrome(chars, cc);
    }

}

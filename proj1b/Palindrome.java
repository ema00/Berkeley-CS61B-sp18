/**
 * Palindrome
 */

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (Character c : word.toCharArray()) {
            deque.addLast(c);
        }
        return deque;
    }

}

/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {

    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator offBy4CC = new OffByN(4);             // CC to find Off-by-4 palindromes

        System.out.println("\nStandard palindromes:");
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
                System.out.println(word);
            }
        }

        in.close();
        in = new In("../library-sp18/data/words.txt");

        System.out.println("\nOff-by-4 palindromes:");
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, offBy4CC)) {
                System.out.println(word);
            }
        }
    }
}

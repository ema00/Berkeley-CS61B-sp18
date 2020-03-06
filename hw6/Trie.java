import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;



/**
 * Trie
 *
 * Trie (retrieval tree) data structure.
 * This implementation can store words using ASCII characters from 'a' to 'z' and space ' '.
 * This implementation uses a Map<Character, TrieNode> to store the children of a node.
 *
 * @author Emanuel Aguirre
 */
public class Trie {

    /* First valid letter. */
    private static final char FIRST_VALID_LETTER = 'a';
    /* Last valid letter. */
    private static final char LAST_VALID_LETTER = 'z';
    /* The only valid character, apart from letters. */
    private static final char SPACE_CHAR = ' ';
    /* Sentinel node that holds references to each first letter of every word set. */
    private CharNode sentinel;


    Trie() {
        sentinel = new CharNode(SPACE_CHAR);
    }


    void addWord(String word) {
        if (word == null || word.equals("")) {
            return;
        }
        for (int i = 0; i < word.length(); i++) {
            if (!isValidChar(word.charAt(i))) {
                return;
            }
        }
        CharNode charNode = sentinel;
        for (int i = 0; i < word.length(); i++) {
            charNode = charNode.addNextLetter(word.charAt(i));
        }
        charNode.end = true;
    }

    boolean isValidChar(char c) {
        return (FIRST_VALID_LETTER <= c && c <= LAST_VALID_LETTER) || c == SPACE_CHAR;
    }

    /**
     * Returns a List of all the words that start with the given prefix.
     * @param prefix is the prefix to use to search for words starting with it.
     * @return a List of all the words that start with the given prefix.
     */
    List<String> wordsByPrefix(String prefix) {
        if (prefix.equals("")) {
            return new ArrayList<>();
        }
        List<String> results = new ArrayList<>();
        CharNode charNode = sentinel;
        for (int i = 0; i < prefix.length(); i++) {
            char letter = prefix.charAt(i);
            if (!charNode.nextLetterMap.containsKey(letter)) {
                return results;
            }
            charNode = charNode.nextLetterMap.get(letter);
        }
        // Remove last letter of prefix because is first letter of suffix (easier implementation)
        prefix = prefix.substring(0, prefix.length() - 1);
        List<String> suffixes = getAllWords(charNode);
        for (String suffix : suffixes) {
            results.add(prefix + suffix);
        }
        return results;
    }

    /**
     * Returns all the words that can be formed with the letters that start with the letter at
     * the node passed as parameter.
     */
    private List<String> getAllWords(CharNode charNode) {
        List<String> words = new ArrayList<>();
        String word = "";
        getAllWords(charNode, word, words);
        return words;
    }

    private void getAllWords(CharNode charNode, String word, List<String> words) {
        if (charNode == null) {
            return;
        }
        if (charNode.end) {
            words.add(word + charNode.letter);
        }
        if (charNode.nextLetterMap == null) {
            return;
        }
        for (CharNode cn : charNode.nextLetterMap.values()) {
            getAllWords(cn, word + charNode.letter, words);
        }
    }


    /**
     * This class represents each node in the trie. Holding a letter, and links to other
     * nodes that correspond to the letters after this one, stored as a Map.
     * The "end" member variable signals that the node is the end of a word. Even though,
     * it does not mean that other words can't be formed with that word, since if the
     * nextLetterMap contains other characters, then other words may be formed.
     */
    private static class CharNode implements Comparable<CharNode> {

        final char letter;
        boolean end;
        Map<Character, CharNode> nextLetterMap;

        CharNode(char letter) {
            this.letter = Character.toLowerCase(letter);
            end = false;
            nextLetterMap = null;
        }

        @Override
        public int compareTo(CharNode other) {
            if (other == null) {
                throw new NullPointerException("CharNode to compare to cannot be null.");
            }
            return this.letter - other.letter;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o.getClass() != this.getClass()) {
                return false;
            }
            CharNode other = (CharNode) o;
            return this.letter == other.letter;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(letter);
        }

        /**
         * Adds a node with the letter following the letter contained by this node. If there is a
         * next node already containing the letter, the new node is not added; this is because the
         * existing node could already have child nodes containing the following letters.
         * @param l is the character that the new node will contain.
         * @return the node containing the recently added letter.
         */
        CharNode addNextLetter(char l) {
            if (nextLetterMap == null) {
                nextLetterMap = new TreeMap<>();
            }
            if (!nextLetterMap.containsKey(l)) {
                nextLetterMap.put(l, new CharNode(l));
            }
            return nextLetterMap.get(l);
        }

    }

    /* Simple test of Trie construction and usage. */
    public static void main(String[] args) {
        /* Test creation of a Trie. */
        Trie trie = new Trie();
        trie.addWord("ana");
        trie.addWord("anana");
        trie.addWord("ananas");
        trie.addWord("banana");
        trie.addWord("bananas");
        /* Test get words that start with a prefix. */
        /* [ana, anana, ananas] */
        System.out.println(trie.wordsByPrefix("a").toString());
        System.out.println(trie.wordsByPrefix("an").toString());
        System.out.println(trie.wordsByPrefix("ana").toString());
        /* [anana, ananas] */
        System.out.println(trie.wordsByPrefix("anan").toString());
        /* [banana, bananas] */
        System.out.println(trie.wordsByPrefix("b").toString());
        /* [bananas] */
        System.out.println(trie.wordsByPrefix("bananas").toString());
    }

}

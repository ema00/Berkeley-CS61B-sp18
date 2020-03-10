import java.util.*;
import java.util.stream.Collectors;


/**
 * Boggle
 * CS61B Homework 6: https://sp18.datastructur.es/materials/hw/hw6/hw6
 *
 * Class that helps find a number of optimal solutions to a Boggle puzzle.
 *
 * @author CS61B staff (interface)
 * @author Emanuel Aguirre (implementation)
 */
public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";   //"trivial_words.txt";

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        In ind = new In(dictPath);
        String[] words = ind.readAllLines();
        Trie dictionary = Trie.create(words);

        In inb = new In(boardFilePath);
        String[] boardLines = inb.readAllLines();
        char[][] letterGrid = new char[boardLines.length][];
        for (int i = 0; i < boardLines.length; i++) {
            letterGrid[i] = boardLines[i].toCharArray();
        }

        BoggleGraph bg = BoggleGraph.create(letterGrid, dictionary);
        List<String> validWords = bg.findValidWords();

        Set<String> uniqueWords = new TreeSet<>(validWords);
        List<String> result = new ArrayList<>(uniqueWords);
        result = result.stream().sorted((s1, s2) -> s2.length() - s1.length())
                .collect(Collectors.toList());

        int length = result.size() > k ? k : result.size();
        return result.subList(0, length);
    }

    /* Simple test of Boggle solver usage. */
    public static void main(String[] args) {
        try {
            String boardFilePath = args[0];
            List<String> solution = solve(7, boardFilePath);
            System.out.println(solution);
        } catch (Exception ex) {
            System.out.println("Error on program argument. Must be the path to the board file. "
                + "Example: exampleBoard.txt" + "\n"
                + ex.getMessage());
        }
    }

}

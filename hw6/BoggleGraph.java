import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;



/**
 * BoggleGraph
 *
 * It is a graph whose nodes represent letters. Represents the grid of the Boggle game.
 * Adjacent letters in the grid, are neighbor nodes.
 *
 * @author Emanuel Aguirre
 */
public class BoggleGraph {

    private final Set<Node> nodes;
    private final Trie trie;


    private BoggleGraph(Trie trie) {
        this.nodes = new HashSet<>();
        this.trie = trie;
    }

    public static BoggleGraph create(char[][] letterGrid, Trie trie) {
        BoggleGraph boggleGraph = new BoggleGraph(trie);
        int height = letterGrid.length;
        int width = letterGrid[0].length;
        Node[][] grid = new Node[height][width];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Node(letterGrid[i][j]);
                boggleGraph.nodes.add(grid[i][j]);
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                addNeighborsInGrid(grid, i, j);
            }
        }
        return boggleGraph;
    }


    /**
     * Since the letters are arranged in a grid, the neighboring letters in the grid are
     * set as neighbors. This method makes neighbors letters in the gird be neighbor nodes
     * in the graph.
     */
    private static void addNeighborsInGrid(Node[][] grid, int row, int col) {
        int height = grid.length;
        int width = grid[0].length;
        for (int i = -1; i <= +1; i++) {
            int r = row + i;
            for (int j = -1; j <= +1; j++) {
                int c = col + j;
                if ((r == row && c == col) || (r < 0 || r >= height) || (c < 0 || c >= width)) {
                    continue;
                }
                Node node = grid[row][col];
                Node neighbor = grid[r][c];
                node.addNeighbor(neighbor);
            }
        }
    }

    /**
     * Finds all the words that are in the Trie, that can be formed by neighbor letters,
     * without repetition of letters.
     * @return a List of the valid words (ones that are in the Trie) that can be formed
     * by neighbor letters without repetition.
     */
    public List<String> findValidWords() {
        List<String> result = new ArrayList<>();
        for (Node node : nodes) {
            List<String> partial = findWordsStartingAt(node, trie);
            result.addAll(partial);
        }
        return result;
    }

    /**
     * Finds all the words that can be formed starting at the given Node in the Graph.
     * Uses the BFS search algorithm to find the words in the graph.
     * To find every word, a Path formed by Nodes, that begin at the start node, is used
     * represent every possible word, as long as that Path represents a prefix of a word
     * stored in the Trie; if a Path does not lead to a valid word, then it is discarded
     * during the search.
     */
    private List<String> findWordsStartingAt(Node start, Trie trie) {
        Queue<Path> fringe = new LinkedList<>();
        List<String> result = new ArrayList<>();
        Path seed = new Path(start);
        fringe.add(seed);
        while (!fringe.isEmpty()) {
            Path path = fringe.poll();
            String word = path.string();
            if (trie.contains(word)) {
                result.add(word);
            }
            if (trie.containsPrefix(word)) {
                Node end = path.end();
                for (Node neighbor : end.neighbors()) {
                    if (!path.contains(neighbor)) {
                        Path extended = (Path) path.clone();
                        extended.extend(neighbor);
                        fringe.add(extended);
                    }
                }
            }
        }
        return result;
    }

}

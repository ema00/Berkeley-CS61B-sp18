package lab11.graphs;

import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Stack;



/**
 * MazeCycles
 * CS61B, Lab 11: https://sp18.datastructur.es/materials/lab/lab11/lab11
 *
 * Explores the maze looking for cycles in the graph. Stops when finds one.
 * This implementation uses an explicit stack to perform DFS.
 *
 * @author Josh Hug (interface)
 * @author Emanuel Aguirre (implementation)
 */
public class MazeCycles extends MazeExplorer {

    /* Inherited public fields: */
    /* Distance from source to each node. */
    /*public int[] distTo;*/
    /* Node previous in the search path. */
    /*public int[] edgeTo;*/
    /* Node has already been explored. */
    /*public boolean[] marked;*/

    private int source;
    private Maze maze;
    private boolean cycleFound;


    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        source = maze.xyTo1D(1, 1);
        cycleFound = false;
        edgeTo[source] = source;
    }


    @Override
    public void solve() {
        findCycle();
        announce();
    }

    private void findCycle() {
        Stack<Integer> stack = new Stack<>();
        edgeTo[source] = source;
        stack.push(source);
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            marked[vertex] = true;
            for (int neighbor : maze.adj(vertex)) {
                if (!marked[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    stack.push(neighbor);
                    announce();
                } else if (marked[neighbor] && existsCycle(vertex, neighbor)) {
                    markCycle(neighbor, vertex);
                    return;
                }
            }
        }
    }

    /**
     * Checks if a cycle is closed by two given vertices.
     * For two adjacent vertices, there exists a cycle if for one of the vertices
     * the second vertex is marked, and it is not the parent of the first vertex.
     * Precondition: Both vertices have to be marked, in a DFS search, and the
     * "first vertex" is the one currently being explored.
     */
    private boolean existsCycle(int first, int second) {
        return marked[first] && marked[second] && !(edgeTo[first] == second);
    }

    /** Marks all the edges that make the cycle found. */
    private void markCycle(int startVertex, int endVertex) {
        List<Integer> cycleVertices = new ArrayList<>();
        int vertex = endVertex;
        edgeTo[startVertex] = endVertex;                // close cycle
        cycleVertices.add(vertex);
        while (vertex != startVertex) {
            for (int neighbor : maze.adj(vertex)) {
                vertex = edgeTo[vertex] == neighbor ? neighbor : vertex;
            }
            cycleVertices.add(vertex);
        }
        for (int v = 0; v < edgeTo.length; v++) {
            edgeTo[v] = cycleVertices.contains(v) ? edgeTo[v] : Integer.MAX_VALUE;
        }
    }

}

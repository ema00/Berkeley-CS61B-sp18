package lab11.graphs;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



/**
 * MazeBreadFirstSearch
 * CS61B, Lab 11: https://sp18.datastructur.es/materials/lab/lab11/lab11
 *
 * Implements a Breadth First Search for a Maze represented as an undirected graph.
 *
 * @author Josh Hug (interface)
 * @author Emanuel Aguirre (implementation)
 */
public class MazeBreadthFirstPaths extends MazeExplorer {

    /* Inherited public fields: */
    /* Distance from source to each node. */
    /*public int[] distTo;*/
    /* Node previous in the search path. */
    /*public int[] edgeTo;*/
    /* Node has already been explored. */
    /*public boolean[] marked;*/

    /* Source and Target Nodes. */
    private int source;
    private int target;

    /* Queue for implementing BFS (the fringe). */
    private final Queue<Integer> fringe;

    private Maze maze;
    private boolean targetFound = false;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        fringe = new ConcurrentLinkedQueue<>();
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
    }


    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        marked[source] = true;
        distTo[source] = 0;
        edgeTo[source] = source;
        targetFound = (source == target);
        fringe.add(source);
        while (!(targetFound || fringe.isEmpty())) {
            int vertex = fringe.remove();
            for (int neighbor : maze.adj(vertex)) {
                if (!marked[neighbor]) {
                    marked[neighbor] = true;
                    distTo[neighbor] = distTo[vertex] + 1;
                    edgeTo[neighbor] = vertex;
                    targetFound = (neighbor == target);
                    fringe.add(neighbor);
                }
                if (targetFound) {
                    break;
                }
            }
            announce();
        }
    }

    @Override
    public void solve() {
        bfs();
    }

}

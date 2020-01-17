package lab11.graphs;

import static java.lang.Math.abs;



/**
 * MazeAStarPath
 * CS61B, Lab 11: https://sp18.datastructur.es/materials/lab/lab11/lab11
 *
 * Implements the A* algorithm for a Maze represented as an undirected graph.
 *
 * @author Josh Hug (interface)
 * @author Emanuel Aguirre (implementation)
 */
public class MazeAStarPath extends MazeExplorer {

    /* Vertex number not valid, to indicate that no solution could be found. */
    private static final int NO_SOLUTION_FOUND = -1;

    private int source;
    private int target;
    private boolean targetFound;
    private Maze maze;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        targetFound = false;
    }


    /** Estimate of the distance from v to the target. Estimation is Manhattan distance. */
    private int h(int v) {
        return abs(maze.toX(target) - maze.toX(v)) + abs(maze.toY(target) - maze.toY(v));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Finds vertex estimated to be closest to target. */
    private int findBestNextVertex() {
        int bestVertex = NO_SOLUTION_FOUND;
        int bestDistance = Integer.MAX_VALUE;
        for (int v = 0; v < distTo.length; v++) {
            if (marked[v]) {
                continue;
            }
            if (distTo[v] == Integer.MAX_VALUE) {
                continue;
            }
            if (distTo[v] + h(v) < bestDistance) {
                bestVertex = v;
            }
        }
        return bestVertex;
    }

    /** Performs an A star search. */
    private void astar() {
        distTo[source] = 0;
        edgeTo[source] = source;

        while (!targetFound) {
            int vertex = findBestNextVertex();
            if (vertex == NO_SOLUTION_FOUND) {
                break;
            }
            marked[vertex] = true;
            targetFound = (target == vertex);
            for (int neighbor : maze.adj(vertex)) {
                if (!marked[neighbor] && distTo[vertex] + 1 < distTo[neighbor]) {
                    distTo[neighbor] = distTo[vertex] + 1;
                    edgeTo[neighbor] = vertex;
                }
            }
            announce();
        }
    }

    @Override
    public void solve() {
        astar();
    }

}

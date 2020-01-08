package hw4.puzzle;

import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;



/**
 * Solver
 *
 * Solves a puzzle (that can be) expressed in terms of a graph by using the A*
 * Search Algorithm.
 * This solver traverses the graph of states, finding a path from start to goal
 * that is saved as a chain of SearchNode instances.
 * For reference, see: https://en.wikipedia.org/wiki/A*_search_algorithm
 *
 * A* uses a Min Priority Queue to find the best path at each step.
 * The function that is used to minimize the path (according to A*) is implemented
 * in the SearchNodeComparator, which is used by the priority queue to get the best
 * path at each step.
 *
 * This solver assumes that a solution exists.
 *
 * @author Emanuel Aguirre
 */
public class Solver {

    private final MinPQ<SearchNode> pq;
    private SearchNode path = null;


    public Solver(WorldState initial) {
        pq = new MinPQ<>(SearchNode.getComparator());

        SearchNode start = new SearchNode(initial, null);
        pq.insert(start);

        while (!pq.isEmpty()) {
            SearchNode current = pq.min();
            pq.delMin();
            WorldState state = current.state();
            if (state.isGoal()) {
                path = current;
                break;
            }

            for (WorldState neighbor : state.neighbors()) {
                SearchNode next = new SearchNode(neighbor, current);
                pq.insert(next);
            }
        }
    }

    public int moves() {
        return path.distanceFromStart();
    }

    public Iterable<WorldState> solution() {
        List<WorldState> solution = new LinkedList<>();
        SearchNode node = path;
        do {
            WorldState state = node.state();
            solution.add(0, state);
            node = node.previousNode();
        } while (node != null);
        return solution;
    }

}

package hw4.puzzle;

import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;



/**
 * CS61B Homework 4: https://sp18.datastructur.es/materials/hw/hw4/hw4
 *
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
            SearchNode currentNode = pq.min();
            pq.delMin();
            WorldState state = currentNode.state();
            if (state.isGoal()) {
                path = currentNode;
                break;
            }

            for (WorldState neighbor : state.neighbors()) {
                // Avoid enqueuing a previous neighbor that has already been enqueued.
                WorldState previousState = currentNode.previousNode() != null
                        ? currentNode.previousNode().state()
                        : null;
                if (neighbor.equals(previousState)) {
                    continue;
                }
                // Add neighbor to the priority queue.
                SearchNode nextNode = new SearchNode(neighbor, currentNode);
                pq.insert(nextNode);
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

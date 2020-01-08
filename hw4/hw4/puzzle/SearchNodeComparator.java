package hw4.puzzle;

import java.util.Comparator;



/**
 * SearchNodeComparator
 *
 * Compares instances of SearchNode based on the A* Search Algorithm approach.
 * This algorithm selects the path that minimizes the sum of: the cost of the path
 * from the start to the current node plus the estimated cost of the cheapest path
 * from the current node to the goal.
 * So, when comparing 2 nodes, the previous sum is the value used for comparison.
 * For reference, see: https://en.wikipedia.org/wiki/A*_search_algorithm
 *
 * Since this comparator is to be used in a Priority Queue, it doesn't matter if
 * 2 different instances are equal by comparison.
 *
 * @author Emanuel Aguirre
 */
public final class SearchNodeComparator implements Comparator<SearchNode> {

    @Override
    public int compare(SearchNode n1, SearchNode n2) {
        if (n1 == null || n2 == null) {
            throw new NullPointerException("SearchNode instances to compare cannot be null.");
        }

        return (n1.distanceFromStart() + n1.estimatedDistanceToGoal())
                - (n2.distanceFromStart() + n2.estimatedDistanceToGoal());
    }

}

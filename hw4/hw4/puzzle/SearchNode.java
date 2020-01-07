package hw4.puzzle;

import java.util.Comparator;



/**
 * SearchNode
 *
 * Represents one “move sequence” in the Search algorithm implemented by the Solver class.
 * That is, the WorldState derived from the start state, and all the previous states that
 * led from start to the current state.
 *
 * Each node in the graph represents a WorldState instance.
 *
 * @author Emanuel Aguirre
 */
public class SearchNode {

    /* Current node in the graph. */
    private final WorldState state;
    /* Previous node in the graph. */
    private final SearchNode previousNode;
    /* Number of nodes traversed from the start node up to this node. */
    private final int moves;


    public SearchNode(WorldState state, SearchNode previousNode) {
        this.state = state;
        this.previousNode = previousNode;
        this.moves = previousNode == null ? 0 : previousNode.moves + 1;
    }


    public WorldState state() {
        return state;
    }

    public SearchNode previousNode() {
        return previousNode;
    }

    public int distanceFromStart() {
        return moves;
    }

    public int estimatedDistanceToGoal() {
        return state.estimatedDistanceToGoal();
    }

    public static Comparator<SearchNode> getComparator() {
        return new SearchNodeComparator();
    }

}

package hw4.puzzle;



/**
 * SearchNode
 * Represents one “move sequence” in the Search algorithm implemented by the Solver class.
 *
 * Consists of the current world state and a link to the previous world state.
 * Also has the number of moves from the start node up to this node.
 *
 * Each node in the graph is represented by a WorldState instance.
 *
 * @author Emanuel Aguirre
 */
public class SearchNode {

    /* Current node in the graph. */
    private final WorldState state;
    /* Previous node in the graph. */
    private final SearchNode prevNode;
    /* Number of nodes traversed from the start node up to this node. */
    private final int moves;


    public SearchNode(WorldState state, SearchNode prevNode) {
        this.state = state;
        this.prevNode = prevNode;
        this.moves = prevNode.moves + 1;
    }

}

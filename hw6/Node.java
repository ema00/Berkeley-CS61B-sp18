import java.util.Set;
import java.util.HashSet;



/**
 * Node
 *
 * It is a node in a graph whose nodes represent letters.
 * Equality is defined based on object equality, since there is no need to define equality
 * based on attributes, and to avoid using and Id field.
 * Hash Code is based on the method inherited from Object class, due to the implementation
 * of equality.
 *
 * @author Emanuel Aguirre
 */
public class Node {

    public final Character letter;
    private final Set<Node> neighbors;


    Node(char letter) {
        this.letter = letter;
        this.neighbors = new HashSet<>();
    }

    
    public Iterable<Node> neighbors() {
        return neighbors;
    }

    public boolean addNeighbor(Node node) {
        if (node == null) {
            throw new NullPointerException("Cannot add null neighbor Node.");
        }
        if (this.equals(node)) {
            return false;
        }
        neighbors.add(node);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

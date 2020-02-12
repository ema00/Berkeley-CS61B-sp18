import java.util.Objects;
import java.util.List;
import java.util.ArrayList;



/**
 * Way
 *
 * Represents a "way" element as described in OSM.
 * Nodes in a way arranged in the order in which they are in the way.
 * https://wiki.openstreetmap.org/wiki/Way
 *
 * @author Emanuel Aguirre
 */
public class Way {

    /* Way Id. */
    final Long id;
    /* Name of the way. */
    final String name;
    /* Maximum speed allowed in this way, in miles per hour (mph). */
    final int maxSpeed;
    /* Nodes that compose the Way element. */
    private List<Node> nodes;


    Way(Long id, String name, int maxSpeed) {
        this.id = id;
        this.name = name;
        this.maxSpeed = maxSpeed;
        nodes = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Way other = (Way) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Adds a node after the other nodes, and makes it neighbor of the previous node.
     * Does allow duplicates.
     * @param node is the Node instance to be added.
     * @return true if node could be added.
     */
    boolean addNode(Node node) {
        if (nodes.size() > 0) {
            Node previous = nodes.get(nodes.size() - 1);
            previous.addNeighbor(node);
            node.addNeighbor(previous);
        }
        return nodes.add(node);
    }

    boolean contains(Node node) {
        return nodes.contains(node);
    }

    Iterable<Node> nodes() {
        return nodes;
    }

}

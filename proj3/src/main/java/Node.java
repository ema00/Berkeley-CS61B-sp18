import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;



/**
 * Node
 *
 * Represents a Node element almost as defined in Open Street Map.
 * More specifically, represents a node that has no attributes other than id, lon and lat.
 * It is composed of a coordinate (latitude and longitude) and a unique Id.
 * It is connected to its neighbor nodes.
 * Has attributes that are taken from those described in OSM, and mapped from String to String.
 * Equality (and hash code) is based on Id. But neighbors are ignored.
 * 
 * @author Emanuel Aguirre
 */
class Node extends Coordinate {

    /* Node Id. */
    final Long id;
    /* Neighbor nodes. Lazily initialized to avoid unnecessary use of memory. */
    private Set<Node> neighbors;


    Node(Long id, double lon, double lat) {
        super(lon, lat);
        this.id = id;
        this.neighbors = null;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Node other = (Node) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Checks if coordinates of this Node are equal to the ones of other node.
     * @param other is other Node instance on which to check coordinate equality.
     * @return true if longitude and latitude coordinates of both nodes are equal.
     */
    boolean equalCoordinates(Node other) {
        return this.lon == other.lon && this.lat == other.lat;
    }

    /**
     * Checks if this node has neighbor nodes.
     * @return true if this node has other neighbor nodes.
     */
    boolean hasNeighbors() {
        return neighbors != null && !neighbors.isEmpty();
    }

    /**
     * Adds a neighbor node to this node, and adds this node as neighbor of the other node.
     * @param neighbor is the node to add as a neighbor.
     * @return true if the neighbor node wasn't already a neighbor.
     */
    boolean addNeighbor(Node neighbor) {
        if (this.neighbors == null) {
            this.neighbors = new HashSet<>();
        }
        if (neighbor.neighbors == null) {
            neighbor.neighbors = new HashSet<>();
        }
        neighbor.neighbors.add(this);
        return this.neighbors.add(neighbor);
    }

    /**
     * @return an Iterable of the neighbor nodes of this node.
     */
    Iterable<Node> neighbors() {
        if (neighbors == null) {
            return new HashSet<>();
        } else {
            return neighbors;
        }
    }

    /**
     * Removes a neighbor Node from this Node. Does not remove this Node as neighbor of the other.
     * @param neighbor is the neighbor Node to remove as a neighbor of this Node.
     * @return true if the removal succeeded.
     */
    boolean removeNeighbor(Node neighbor) {
        return neighbors.remove(neighbor);
    }

    void removeNeighbors() {
        if (!hasNeighbors()) {
            return;
        }
        Iterator<Node> iterator = this.neighbors().iterator();
        while (iterator.hasNext()) {
            Node neighbor = iterator.next();
            neighbor.removeNeighbor(this);
            iterator.remove();
        }
    }

}

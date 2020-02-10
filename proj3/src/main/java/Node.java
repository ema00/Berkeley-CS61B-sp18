import java.util.Objects;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;



/**
 * Node
 *
 * Represents a Node element as defined in Open Street Map.
 * It is composed of a coordinate (latitude and longitude) and an unique Id.
 * It is connected to its neighbor nodes.
 * Has attributes that are taken from those described in OSM, and mapped from String to String.
 * Node equality (and hash code) are based on latitude, longitude and Id. But neighbors and
 * attributes are ignored.
 * 
 * @author Emanuel Aguirre
 */
class Node extends Coordinate {

    /* Node Id. */
    final long id;
    /* Neighbor nodes. Lazily initialized to avoid unnecessary use of memory. */
    private Set<Node> neighbors;
    /* Node attributes, according to OSM. Lazily initialized to avoid unnecessary use of memory. */
    private Map<String, String> attributes;


    Node(double lon, double lat, long id) {
        super(lon, lat);
        this.id = id;
        this.neighbors = null;
        this.attributes = null;
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
        return super.equals(other) && this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat, id);
    }

    /**
     * Checks if coordinates of this Node are equal to th ones of other node.
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
     * Returns true if this node has attributes; that is if it is not just a coordinate in the map.
     * @return true if this node has at least one attribute.
     */
    boolean hasAttributes() {
        return attributes != null && !attributes.isEmpty();
    }

    /**
     * Checks if a this node has an attribute whose name is passed as parameter.
     * @param key is the name of the attribute to check if the node has.
     * @return true if this node contains an attribute with the given value.
     */
    boolean containsAttributeKey(String key) {
        return attributes != null && attributes.containsKey(key);
    }

    /**
     * Gets the corresponding value for the attribute passed as parameter.
     * @param key is the name of the attribute that is to be checked for belonging to the node.
     * @return the value mapped to the attribute, or null if the node does not have the attribute.
     */
    String getAttributeValue(String key) {
        if (attributes == null) {
            return null;
        } else {
            return attributes.get(key);
        }
    }

    /**
     * Returns an Iterable of the names of the attributes that this node has.
     * @return an Iterable of the names of the attributes that this node has.
     */
    Iterable<String> attributeKeys() {
        if (attributes == null) {
            return new HashMap<String, String>().keySet();
        } else {
            return attributes.keySet();
        }
    }

    /**
     * Returns an Iterable of the values of the attributes that this node has.
     * @return an Iterable of the values of the attributes that this node has.
     */
    Iterable<String> attributeValues() {
        if (attributes == null) {
            return new HashMap<String, String>().values();
        } else {
            return attributes.values();
        }
    }

    /**
     * Adds an attribute and its corresponding value to this node.
     * @param key is the name of the attribute to be added.
     * @param value is the value of the attribute to be added.
     * @return the previous value associated with the attribute name.
     */
    String addAttribute(String key, String value) {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes.put(key, value);
    }

}

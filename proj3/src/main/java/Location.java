import java.util.Objects;
import java.util.Map;
import java.util.HashMap;



/**
 * Location
 *
 * Represents a Node element almost as defined in Open Street Map.
 * More specifically, represents an OSM node that has attributes apart from id, lon and lat.
 * It is composed of a Node and attributes.
 * The Node instance wrapped by the location may be connected to neighbor nodes.
 * Has attributes that are taken from those described in OSM, and mapped from String to String.
 * Equality (and hash code) is based on the Id of the Node instance. But attributes are ignored.
 * Equality is calculated this way since this is an OSM node that has a name (and attributes).
 *
 * @author Emanuel Aguirre
 */
public class Location {

    /* Location Id. */
    final long id;
    /* Node. */
    final Node node;
    /* Location (node) attributes, according to OSM. */
    private Map<String, String> attributes;


    Location(Node node) {
        this.id = node.id;
        this.node = node;
        this.attributes = new HashMap<>();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Location other = (Location) o;
        return this.node.id == other.node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node.id);
    }

    /**
     * Checks if a this location has an attribute whose name is passed as parameter.
     * @param key is the name of the attribute to check if the location has.
     * @return true if this location contains an attribute with the given key.
     */
    boolean containsAttributeKey(String key) {
        return attributes.containsKey(key);
    }

    /**
     * Gets the corresponding value for the attribute passed as parameter.
     * @param key is the name of the attribute that is to be checked for belonging to the location.
     * @return the value mapped to the attribute, or null if the location does not have it.
     */
    String getAttributeValue(String key) {
        return attributes.get(key);
    }

    /**
     * Returns an Iterable of the names of the attributes that this location has.
     * @return an Iterable of the names of the attributes that this location has.
     */
    Iterable<String> attributeKeys() {
        return attributes.keySet();
    }

    /**
     * Returns an Iterable of the values of the attributes that this location has.
     * @return an Iterable of the values of the attributes that this location has.
     */
    Iterable<String> attributeValues() {
        return attributes.values();
    }

    /**
     * Sets an attribute and its corresponding value for this location.
     * @param key is the name of the attribute to be added.
     * @param value is the value of the attribute to be added.
     * @return the previous value associated with the attribute name.
     */
    String setAttribute(String key, String value) {
        return attributes.put(key, value);
    }

}

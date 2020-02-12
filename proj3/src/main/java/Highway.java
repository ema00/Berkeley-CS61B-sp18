


/**
 * Way
 *
 * Represents a "highway" element (a type of "way") as described in OSM.
 * https://wiki.openstreetmap.org/wiki/Key:highway
 *
 * @author Emanuel Aguirre
 */
public class Highway extends Way {

    /* Type of highway according to OSM: motorway, trunk, primary, secondary, tertiary, etc. */
    final String type;


    Highway(long id, String name, String type, int maxSpeed) {
        super(id, name, maxSpeed);
        this.type = type;
    }

}

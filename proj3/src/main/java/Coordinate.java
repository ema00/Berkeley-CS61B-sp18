import java.util.Objects;



/**
 * Coordinate
 *
 * Represents a coordinate point on the surface of earth, completely determined by its
 * longitude and latitude coordinates.
 *
 * @author Emanuel Aguirre
 */
class Coordinate {

    /* Longitude coordinate. */
    final double lon;
    /* Latitude coordinate. */
    final double lat;


    Coordinate(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
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
        return this.lon == other.lon && this.lat == other.lat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat);
    }

}

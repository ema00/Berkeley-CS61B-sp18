import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;



/**
 * GraphDB
 * CS61B, Project 3: https://sp18.datastructur.es/materials/proj/proj3/proj3
 *
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 * @author Emanuel Aguirre (nodes, locations, highways, autocomplete and some implementation)
 */
public class GraphDB {

    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    /* All the nodes (vertices) found when parsing the OSM XML. */
    private Map<Long, Node> nodes;
    /* All the locations (nodes that have attributes in OSM) found when parsing the OSM XML. */
    private HashMap<Long, Location> locations;
    /* All the valid highways found when parsing the OSM XML. */
    private HashMap<Long, Highway> highways;
    /* Trie data structure, contains the cleaned location names, Used for autocomplete feature. */
    private Trie cleanLocationNames;
    /* Maps the cleaned location names to locations themselves. Used for autocomplete feature. */
    private Map<String, List<Location>> locationsByCleanName;


    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        nodes = new HashMap<>();
        locations = new HashMap<>();
        highways = new HashMap<>();
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
            initializeGetLocationByPrefix();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }


    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        Iterator<Long> iterator = nodes.keySet().iterator();
        nodes.values().removeIf(n -> !n.hasNeighbors());
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        List<Long> neighborIds = new ArrayList<>();
        for (Node node : nodes.get(v).neighbors()) {
            neighborIds.add(node.id);
        }
        return neighborIds;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        Iterator<Node> iterator = nodes.values().iterator();
        Node closest = iterator.next();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (distance(lon, lat, node.lon, node.lat)
                    < distance(lon, lat, closest.lon, closest.lat)) {
                closest = node;
            }
        }
        return closest.id;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodes.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodes.get(v).lat;
    }


    /* Methods for implementing Autocomplete and Search. */

    /**
     * Initializes the Trie cleanLocationNames, and the Map locationsByCleanName, that
     * are used by methods getLocationsByPrefix and getLocations.
     */
    private void initializeGetLocationByPrefix() {
        cleanLocationNames = new Trie();
        locationsByCleanName = new HashMap<>();
        for (Location location : locations.values()) {
            if (location.containsAttributeKey("name")) {
                String locationName = location.getAttributeValue("name");
                String cleanLocationName = cleanString(locationName);
                cleanLocationNames.addWord(cleanLocationName);
                if (!locationsByCleanName.containsKey(cleanLocationName)) {
                    locationsByCleanName.put(cleanLocationName, new ArrayList<>());
                }
                List<Location> locsByCleanName = locationsByCleanName.get(cleanLocationName);
                locsByCleanName.add(location);
            }
        }
    }

    /**
     * In linear time, collect all the OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A List of the full names of locations whose cleaned name matches the
     * cleaned prefix.
     */
    List<Location> getLocationsByPrefix(String prefix) {
        prefix = prefix.toLowerCase();
        List<String> cleanNames = cleanLocationNames.wordsByPrefix(prefix);
        List<Location> result = new ArrayList<>();
        for (String cleanName : cleanNames) {
            List<Location> partial = locationsByCleanName.get(cleanName);
            result.addAll(partial);
        }
        return result;
    }

    /**
     * Collect all locations that match a cleaned locationName, and return information about
     * each node that matches.
     * @param locationName A full clean name of a location searched for.
     * @return A list of locations whose cleaned name matches the cleaned locationName.
     * NOTE: A particular location may span many Location instances. I.e.: may occupy many nodes.
     */
    List<Location> getLocationsByCleanName(String locationName) {
        return locationsByCleanName.getOrDefault(locationName, new ArrayList<>());
    }


    /* Helper methods. */

    Iterable<Node> nodes() {
        return nodes.values();
    }

    Node putNode(Node node) {
        return nodes.put(node.id, node);
    }

    Node getNode(Long id) {
        return nodes.get(id);
    }

    Node removeNode(Long id) {
        return nodes.remove(id);
    }

    int numNodes() {
        return nodes.size();
    }

    Location putLocation(Location location) {
        return locations.put(location.id, location);
    }

    Location getLocation(Long id) {
        return locations.get(id);
    }

    Highway putHighway(Highway highway) {
        return highways.put(highway.id, highway);
    }

    Highway getHighway(Long id) {
        return highways.get(id);
    }

}

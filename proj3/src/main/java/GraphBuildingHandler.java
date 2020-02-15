import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;



/**
 * GraphBuildingHandler
 * CS61B, Project 3: https://sp18.datastructur.es/materials/proj/proj3/proj3
 *
 *  Parses OSM XML files using an XML SAX parser. Used to construct the graph of roads for
 *  pathfinding, under some constraints.
 *  See OSM documentation on
 *  <a href="https://wiki.openstreetmap.org/wiki/OSM_XML">OSM XML file format</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Key:highway">the highway tag</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Way">the way XML element</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Node">the node XML element</a>,
 *  and the java
 *  <a href="https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html">SAX parser tutorial</a>.
 *
 *  You may find the CSCourseGraphDB and CSCourseGraphDBHandler examples useful.
 *
 *  The idea here is that some external library is going to walk through the XML
 *  file, and your override method tells Java what to do every time it gets to the next
 *  element in the file. This is a very common but strange-when-you-first-see it pattern.
 *  It is similar to the Visitor pattern we discussed for graphs.
 *
 *  @author Alan Yao, Maurice Lee
 *  @author Emanuel Aguirre (some code in implementation)
 */
public class GraphBuildingHandler extends DefaultHandler {

    /**
     * Only allow for non-service roads; this prevents going on pedestrian streets as much as
     * possible. Note that in Berkeley, many of the campus roads are tagged as motor vehicle
     * roads, but in practice we walk all over them with such impunity that we forget cars can
     * actually drive on them.
     */
    private static final Set<String> ALLOWED_HIGHWAY_TYPES = new HashSet<>(Arrays.asList
            ("motorway", "trunk", "primary", "secondary", "tertiary", "unclassified",
                    "residential", "living_street", "motorway_link", "trunk_link", "primary_link",
                    "secondary_link", "tertiary_link"));
    private String activeState = "";
    private final GraphDB g;

    /* Variables used for parsing sub-elements of an element. */
    /* Current node. When parsing, ti can be a Node (no attributes) or a Location. */
    private Node currentNode = null;
    /* Id of the Way being parsed. */
    private Long wayId;
    /* Nodes that are parsed when parsing a way. Are discarded if way is not a highway. */
    private List<Node> nodesOfWay = new ArrayList<>();
    /* Name of the Way being parsed. */
    private String wayName;
    /* Speed of a way being parsed, in miles per hour. */
    private int waySpeedMph;
    /* Type of Highway being parsed. */
    private String highwayType;


    /**
     * Create a new GraphBuildingHandler.
     * @param g The graph to populate with the XML data.
     */
    public GraphBuildingHandler(GraphDB g) {
        this.g = g;
    }


    /**
     * Called at the beginning of an element. Typically, you will want to handle each element in
     * here, and you may want to track the parent element.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available. This tells us which element we're looking at.
     * @param attributes The attributes attached to the element. If there are no attributes, it
     *                   shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see Attributes
     */
    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes
    ) throws SAXException {
        /* Some example code on how you might begin to parse XML files. */
        if (qName.equals("node")) {
            /* We encountered a new <node...> tag. */
            activeState = "node";
            Long id = Long.parseLong(attributes.getValue("id"));
            double lon = Double.parseDouble(attributes.getValue("lon"));
            double lat = Double.parseDouble(attributes.getValue("lat"));
            Node node = new Node(id, lon, lat);
            currentNode = node;
            g.putNode(node);
        } else if (qName.equals("way")) {
            /* We encountered a new <way...> tag. */
            activeState = "way";
            /* Get the Id of thw Way. */
            wayId = Long.parseLong(attributes.getValue("id"));
        } else if (activeState.equals("way") && qName.equals("nd")) {
            /* While looking at a way, we found a <nd...> tag. */
            Long nodeId = Long.parseLong(attributes.getValue("ref"));
            Node nodeOfWay = g.getNode(nodeId);
            nodesOfWay.add(nodeOfWay);
        } else if (activeState.equals("way") && qName.equals("tag")) {
            /* While looking at a way, we found a <tag...> tag. */
            String k = attributes.getValue("k");
            String v = attributes.getValue("v");
            if (k.equals("maxspeed")) {
                waySpeedMph = parseSpeed(v);
            } else if (k.equals("highway")) {
                highwayType = attributes.getValue("v");
            } else if (k.equals("name")) {
                wayName = attributes.getValue("v");
            }
        } else if (activeState.equals("node") && qName.equals("tag") && attributes.getValue("k")
                .equals("name")) {
            /* While looking at a node, we found a <tag...> with k="name". */
            Location location = new Location(currentNode);
            location.setAttribute("name", attributes.getValue("v"));
            g.putLocation(location);
        }
    }

    /**
     * Receive notification of the end of an element. You may want to take specific terminating
     * actions here, like finalizing vertices or edges found.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available.
     * @throws SAXException  Any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("way")) {
            /* We are done looking at a way. (We finished looking at the nodes, speeds, etc...)*/
            /* Connect the nodes together if the way is valid. */
            if (ALLOWED_HIGHWAY_TYPES.contains(highwayType)) {
                Highway highway = new Highway(wayId, wayName, highwayType, waySpeedMph);
                for (Node node : nodesOfWay) {
                    highway.addNode(node);
                }
                g.putHighway(highway);
            }
            nodesOfWay.clear();
            highwayType = "";
        }
    }

    /**
     * Parses the speed of a node, and converts it to mph. Since it can be in km/h, mph, or knots.
     * @param v is a String that contains the maximum allowed speed.
     * @return an int with the value of the maximum speed, in miles per hour.
     */
    private int parseSpeed(String v) {
        int speedMph;
        String[] speedTokens = v.split(" ");
        if (speedTokens.length == 1) {
            speedMph = (int) (Integer.parseInt(speedTokens[0]) * 0.621371);  // km/h
        } else {
            speedMph = speedTokens[1].equals("mph")
                    ? Integer.parseInt(speedTokens[0])                          // mph
                    : (int) (Integer.parseInt(speedTokens[0]) * 1.15078);       // knots
        }
        return speedMph;
    }

}

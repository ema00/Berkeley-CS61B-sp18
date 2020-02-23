import java.util.Objects;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Router
 * CS61B, Project 3: https://sp18.datastructur.es/materials/proj/proj3/proj3
 *
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 *
 * @author CS61B staff (interface and comments)
 * @author Emanuel Aguirre (implementation of SearchNode and shortestPath)
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * NOTE: Since the implementation of the Priority Queue provided by Java does not allow to
     * alter the priorities of its nodes, the updating has to be done by removal and insertion.
     * Precondition: node closest to start and node closest to destination must be connected.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(
            GraphDB g, double stlon, double stlat, double destlon, double destlat
    ) {
        // Start and destination Nodes.
        Node start = g.getNode(g.closest(stlon, stlat));
        Node dest = g.getNode(g.closest(destlon, destlat));
        // Nodes for which the lowest value for f(n) has been determined.
        Map<Long, SearchNode> visited = new HashMap<>();
        // Fringe (the PQ used to explore).
        PriorityQueue<SearchNode> fringe = new PriorityQueue<>();
        // Determine the lowest value of f(n) for all the nodes from start to dest.
        SearchNode current = new SearchNode(start, null, dest);
        fringe.add(current);
        while (!fringe.isEmpty()) {
            current = fringe.remove();
            Node node = current.node;
            visited.put(node.id, current);
            if (node.equals(dest)) {
                break;
            }
            for (Node neighbor : node.neighbors()) {
                SearchNode next = new SearchNode(neighbor, current, dest);
                if (visited.containsKey(next.node.id)) {
                    continue;
                }
                if (fringe.contains(next)) {
                    fringe.removeIf(sn -> next.equals(sn) && sn.fn() > next.fn());
                }
                if (!fringe.contains(next)) {
                    fringe.add(next);
                }
            }
        }
        // Start from dest, follow back the chain of search nodes until start, save each in path.
        List<Long> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current.node.id);
            current = current.previous;
        }
        return path;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigationDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }

    }


    /* ******** Classes below are used to implement shortest path using A* algorithm. ******** */

    /**
     * SearchNode
     *
     * This class is used to perform A* search on a graph.
     * Since the Node class offered by GraphDB just represents nodes and its connections, this
     * class is needed to add the ability to perform A* search.
     * This class holds values for f(n), g(n) and h(n) as defined in A*.
     * g(n) is calculated as the distance between the Node contained in an instance and the Node
     * contained in the instance referenced by the "previous" instance member.
     * h(n) is calculated as the distance between the instance and the "target" Node.
     * Implements Comparable<T> on the value of f(n), since this is the value used as the priority
     * in the Priority Queue used as the fringe in A*.
     */
    private static class SearchNode implements Comparable<SearchNode> {

        private final Node node;
        private SearchNode previous;
        private double gn;
        private final double hn;

        SearchNode(Node n, SearchNode prev, Node target) {
            this.node = n;
            this.previous = prev;
            this.gn = prev != null
                    ? prev.gn + GraphDB.distance(prev.node.lon, prev.node.lat, node.lon, node.lat)
                    : 0;
            this.hn = GraphDB.distance(node.lon, node.lat, target.lon, target.lat);
        }

        /**
         * Compares 2 search nodes based on th f(n) = g(n) + h(n) function of A* algorithm.
         * @param other the other object on which to perform comparison.
         * @return -1 if the other node has a smaller f(n), +1 if this one's is smaller, or else 0.
         */
        @Override
        public int compareTo(SearchNode other) {
            if (other == null) {
                throw new NullPointerException("SearchNode instance to compare to cannot be null.");
            }
            double fnt = this.fn();
            double fno = other.fn();
            return Double.compare(fnt, fno);
        }

        /**
         * Returns true if the Node object that this class wraps is the same in both instances.
         * @param o the other object on which to compare for equality.
         * @return true is the id of the wrapped node is the same.
         */
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this.getClass() != o.getClass()) {
                return false;
            }
            SearchNode other = (SearchNode) o;
            return this.node.equals(other.node);
        }

        @Override
        public int hashCode() {
            return this.node.hashCode();
        }

        double gn() {
            return gn;
        }

        double hn() {
            return hn;
        }

        double fn() {
            return gn + hn;
        }

    }

}

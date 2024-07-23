// --== CS400 File Header Information ==--
// Name: Darshan Golchha
// Email: dgolchha@wisc.edu
// Group and Team: G15
// Group TA: Connor Bailey
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * 
     * @param map the map that the graph uses to map a data object to the node
     *            object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // Throwing an exception if the start or end node is not present in the graph
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            throw new NoSuchElementException("Start or End node Not Present");
        }
        // Creating a priority queue to store the nodes in the order of their cost
        PriorityQueue<SearchNode> queue = new PriorityQueue<>();
        // Creating a map to store the visited nodes
        PlaceholderMap<NodeType, SearchNode> visited = new PlaceholderMap<>();
        // Adding the start node to the queue, with a cost of 0 and no predecessor
        SearchNode current = new SearchNode(nodes.get(start), 0, null);
        queue.add(current);
        // Starting a while loop to iterate through the queue
        while (!queue.isEmpty()) {
            // Removing the node with the lowest cost from the queue
            current = queue.remove();
            // Checking if the node has already been visited
            if (!visited.containsKey(current.node.data)) {
                // Checking if the current node is the end node
                if (current.node.data.equals(end)) {
                    // If yes, then returning the current node
                    return current;
                }
                // Adding the current node to the visited map
                visited.put(current.node.data, current);
                // Iterating through the edges leaving the current node
                for (Edge edge : current.node.edgesLeaving) {
                    // Checking if the successor node has already been visited
                    if (!visited.containsKey(edge.successor.data)) {
                        // If not, then adding the successor node to the queue
                        queue.add(new SearchNode(edge.successor, current.cost + edge.data.doubleValue(), current));
                    }
                }
            }
        }
        // Throwing an exception if the end node is not found
        if (!current.node.data.equals(end)) {
            throw new NoSuchElementException("No Path Found");
        }
        // Returning the current node
        return current;
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // Calling the computeShortestPath method to get the shortest path
        SearchNode pathNode = computeShortestPath(start, end);
        // Creating a list to store the data values of the nodes in the shortest path
        List<NodeType> result = new LinkedList<>();
        // Iterating through the nodes in the shortest path
        while (pathNode != null) {
            // Adding the data value of the node to the result list
            result.add(0, pathNode.node.data);
            // Updating the pathNode to the predecessor node
            pathNode = pathNode.predecessor;
        }
        // Returning the result list
        return result;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // Calling the computeShortestPath method to get the shortest path
        SearchNode pathNode = computeShortestPath(start, end);
        // Returning the cost of the shortest path
        return pathNode.cost;
    }

    // TODO: implement 3+ tests in step 4.1

    @Test
    void testStringNodePaths() {
        // creating the graph object to store the nodes and edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // creating and adding nodes and edges to the graph
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "F", 4);

        // testing the shortestPathData method from node A to node F
        List<String> actualPathA = graph.shortestPathData("A", "F");
        List<String> expectedPathA = new LinkedList<>();
        // adding the nodes in the expected path to the list
        for (String node : new String[] { "A", "B", "D", "F" }) {
            expectedPathA.add(node);
        }
        // checking if the actual path is equal to the expected path
        assertTrue(actualPathA.equals(expectedPathA));

        // testing the shortestPathData method from node A to node E
        List<String> actualPathB = graph.shortestPathData("A", "E");
        List<String> expectedPathB = new LinkedList<>();
        // adding the nodes in the expected path to the list
        for (String node : new String[] { "A", "B", "D", "E" }) {
            expectedPathB.add(node);
        }
        // checking if the actual path is equal to the expected path
        assertTrue(actualPathB.equals(expectedPathB));
    }

    @Test
    void testStringNodeCosts() {
        // creating the graph object to store the nodes and edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // creating and adding nodes and edges to the graph
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        // testing the shortestPathCost method from node B to node E
        double actualCostA = graph.shortestPathCost("B", "E");
        double expectedCostA = 4;
        assertEquals(expectedCostA, actualCostA);

        // testing that path taken from B to E
        List<String> actualPathA = graph.shortestPathData("B", "E");
        List<String> expectedPathA = new LinkedList<>();
        // adding the nodes in the expected path to the list
        for (String node : new String[] { "B", "D", "E" }) {
            expectedPathA.add(node);
        }
        // checking if the actual path is equal to the expected path
        assertTrue(actualPathA.equals(expectedPathA));

        // testing the shortestPathCost method from node C to node H
        double actualCostB = graph.shortestPathCost("C", "H");
        double expectedCostB = 9;
        // checking if the actual cost is equal to the expected cost
        assertEquals(expectedCostB, actualCostB);

        // testing the path taken from C to H
        List<String> actualPathB = graph.shortestPathData("C", "H");
        List<String> expectedPathB = new LinkedList<>();
        // adding the nodes in the expected path to the list
        for (String node : new String[] { "C", "D", "F", "H" }) {
            expectedPathB.add(node);
        }
        // checking if the actual path is equal to the expected path
        assertTrue(actualPathB.equals(expectedPathB));
    }

    @Test
    void testDisconnetedNode() {
        // creating the graph object to store the nodes and edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // creating and adding nodes and edges to the graph
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "F", 4);

        // In the above graph, nodes G connect to H in the opposite direction
        // Hence, the path A to G is not possible due to the absense of this connected
        // edge
        try {
            graph.shortestPathData("A", "G");
            fail("An exception should have been thrown since the path from A to G is not possible here");
        } catch (NoSuchElementException e) {
            // Since the path is not possible, the exception was
            // thrown and the test case is passed
        }
    }

    @Test
    void testComputeShortestPaths() {
        // creating the graph object to store the nodes and edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // creating and adding nodes and edges to the graph
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");

        graph.insertEdge("A", "E", 1);
        graph.insertEdge("A", "C", 3);
        graph.insertEdge("A", "F", 5);
        graph.insertEdge("E", "D", 4);
        graph.insertEdge("C", "E", 1);
        graph.insertEdge("C", "F", 5);
        graph.insertEdge("D", "C", 3);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("B", "D", 2);
        graph.insertEdge("B", "C", 3);
        graph.insertEdge("B", "G", 4);
        graph.insertEdge("F", "B", 4);
        graph.insertEdge("G", "F", 2);
        graph.insertEdge("G", "E", 5);

        // testing the computeShortestPath method from node A to node F
        DijkstraGraph<String, Integer>.SearchNode actualPathA = graph.computeShortestPath("A", "G");
        List<String> expectedPathA = new LinkedList<>();
        // adding the nodes in the expected path to the list
        for (String node : new String[] { "A", "E", "D", "G" }) {
            expectedPathA.add(node);
        }
        // checking if the actual path is equal to the expected path
        assertTrue(actualPathA.node.data.equals("G"));
        // checking if the actual cost is equal to the expected cost
        assertTrue(actualPathA.cost == 7);
        // checking each predecessor node in the path
        for (int i = expectedPathA.size() - 1; i == 0; i--) {
            // checking if the predecessor node is equal to the expected node
            assertTrue(actualPathA.node.data.equals(expectedPathA.get(i)));
            // updating the actual path node to the predecessor node
            actualPathA = actualPathA.predecessor;
        }
    }
}

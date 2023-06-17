import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The graph class that is constructed from the given map.
 * @author Emre Oytun
 */
public class CSE222Graph {
    private Map<Node, List<Edge>> edges = new LinkedHashMap<>();
    private Node startNode;
    private Node endNode;
    
    /**
     * The constructor that gives the map as parameter and constructs the graph accordingly.
     * @param map The map that is read from the txt file.
     * @throws Exception If start or end node is invalid such that they are out of boundaries or they are obstacle.
     */
    public CSE222Graph(CSE222Map map) throws Exception {
        constructGraph(map);
    }

    private void constructGraph(CSE222Map map) throws Exception {
        int n = map.getLength();
        startNode = new Node(map.startX(), map.startY());
        endNode = new Node(map.endX(), map.endY());

        // Check the validity of start and end nodes.
        // 1) They must not be obstacle.
        // 2) They must not be out of the map.
        if (startNode.getX() < 0 ||  startNode.getX() >= n || startNode.getY() < 0 || startNode.getY() >= n) {
            throw new Exception("Start node indexes are not valid.");
        }
        if (endNode.getX() < 0 ||  endNode.getX() >= n || endNode.getY() < 0 || endNode.getY() >= n) {
            throw new Exception("End node indexes are not valid.");
        }
        if (map.get(startNode.getY(), startNode.getX()) == 1) {
            throw new Exception("Start node cannot be an obstacle");
        }
        if (map.get(endNode.getY(), endNode.getX()) == 1) {
            throw new Exception("End node cannot be an obstacle");
        }

        // Traverses through the map and creates the vertexes and edges accordingly.  
        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col) {

                if (map.get(row, col) == 0) {

                    Node newNode = new Node(col, row);
                    List<Edge> edgeList = new ArrayList<>();
                    
                    if (col-1 >= 0) {
                        if (map.get(row, col-1) == 0) {
                            Node neighbor = new Node(col-1, row);
                            edgeList.add(new Edge(newNode, neighbor));
                        }
                        if (row-1 >= 0 && map.get(row-1, col-1) == 0) {
                            Node neighbor = new Node(col-1, row-1);
                            edgeList.add(new Edge(newNode, neighbor));
                        }
                        if (row+1 < n && map.get(row+1, col-1) == 0) {
                            Node neighbor = new Node(col-1, row+1);
                            edgeList.add(new Edge(newNode, neighbor));
                        }
                    }
                    
                    if (col+1 < n) {
                        if (map.get(row, col+1) == 0) {
                            Node neighbor = new Node(col+1, row);
                            edgeList.add(new Edge(newNode, neighbor));
                        }
                        if (row-1 >= 0 && map.get(row-1, col+1) == 0) {
                            Node neighbor = new Node(col+1, row-1);
                            edgeList.add(new Edge(newNode, neighbor));
                        }
                        if (row+1 < n && map.get(row+1, col+1) == 0) {
                            Node neighbor = new Node(col+1, row+1);
                            edgeList.add(new Edge(newNode, neighbor));
                        }
                    }

                    if (row-1 >= 0 && map.get(row-1, col) == 0) {
                        Node neighbor = new Node(col, row-1);
                        edgeList.add(new Edge(newNode, neighbor));
                    }
                    if (row+1 < n && map.get(row+1, col) == 0) {
                        Node neighbor = new Node(col, row+1);
                        edgeList.add(new Edge(newNode, neighbor));
                    }
                    
                    edges.put(newNode, edgeList);
                }
            }
        }
    }

    /**
     * Gives an edge iterator of the given node.
     * @param source The node whose edge iterator will be returned.
     * @return An iterator of edges for the given node.
     */
    public Iterator<Edge> edgeIterator(Node source) {
        return edges.get(source).iterator();
    }

    /**
     * Gives a node iterator to traverse all nodes in the graph.
     * @return An iterator of node that traverses all nodes.
     */
    public Iterator<Node> nodeIterator() {
        return edges.keySet().iterator();
    }

    /**
     * Checks if there is an edge which connects the given nodes.
     * @param source The source node of the edge.
     * @param dest The destination node of the edge.
     * @return A boolean indicating if there is such edge.
     */
    public boolean isEdge(Node source, Node dest) {
        Edge edge = new Edge(source, dest);
        return edges.get(source).contains(edge);
    }

    /**
     * Gets the total number of nodes.
     * @return An integer that is the number of nodes.
     */
    public int nodeSize() {
        return edges.size();
    }

    /**
     * Gets the start node.
     * @return A node representing the start node.
     */
    public Node startNode() {
        return startNode;
    }

    /**
     * Gets the end node.
     * @return A node representing the end node.
     */
    public Node endNode() {
        return endNode;
    }
    
}

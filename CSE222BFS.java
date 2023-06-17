import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This class includes the breadth-first search algorithm using the given graph.
 * @author Emre Oytun
 */
public class CSE222BFS {
    private CSE222Graph graph;
    private int length;

    /**
     * Constructor that gets the graph.
     * @param graph The graph that bfs algorithm will be applied on.
     */
    public CSE222BFS(CSE222Graph graph) {
        this.graph = graph;
    }

     /**
     * Returns the length of the bfs result.
     * @return An integer representing the length of the result.
     */
    public int length() {
        return length;
    }

    /**
     * This method finds path from start node to the end node using the given graph with bfs algorithm.
     * @return List of nodes that bfs algorithm finds.
     * @throws Exception If there is no paths found.
     */
    public List<Node> findPath() throws Exception {
        Set<Node> visitedNodes = new HashSet<>();
        Map<Node, Node> parent = new LinkedHashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.offer(graph.startNode());
        visitedNodes.add(graph.startNode());
        parent.put(graph.startNode(), graph.startNode());
        
        boolean isFound = false;
        while (!queue.isEmpty() && !isFound) {
            Node source = queue.poll();
            if (source.equals(graph.endNode())) {
                isFound = true;
            }
            else {
                Iterator<Edge> edgeIterator = graph.edgeIterator(source);
                while (edgeIterator.hasNext()) {
                    Node dest = edgeIterator.next().getDest();
                    if (!visitedNodes.contains(dest)) {
                        queue.offer(dest);
                        visitedNodes.add(dest);
                        parent.put(dest, source);
                    }
                }
            }  
        }

        if (!isFound) {
            throw new Exception("No feasible paths found!");
        }
        List<Node> result = new ArrayList<>();
        Node curNode = graph.endNode();
        while (!parent.get(curNode).equals(curNode)) {
            result.add(curNode);
            curNode = parent.get(curNode);
        }
        result.add(graph.startNode());
        Collections.reverse(result);
        length = result.size() - 1;
        return result;
    }
}
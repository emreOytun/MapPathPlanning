import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * This class includes the dijkstra algorithm using the given graph.
 * @author Emre Oytun
 */
public class CSE222Dijkstra {
    private CSE222Graph graph;
    private int length;

    /**
     * Constructor that gets the graph.
     * @param graph The graph that dijkstra algorithm will be applied on.
     */
    public CSE222Dijkstra(CSE222Graph graph) {
        this.graph = graph;
    }

    /**
     * Returns the length of the dijkstra result.
     * @return An integer representing the length of the result.
     */
    public int length() {
        return length;
    }

     /**
     * This method finds path from start node to the end node using the given graph with dijkstra algorithm.
     * @return List of nodes that dijkstra algorithm finds.
     * @throws Exception If there is no paths found.
     */
    public List<Node> findPath() throws Exception {
        Node startNode = graph.endNode();
        Node endNode = graph.startNode();

        // Initialize distance priority queue by giving comparator and set the distances by checking if there is an edge.
        Queue<Node> distancePq = new PriorityQueue<>(new NodeComparatorDistance());
        Set<Node> visitedSet = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Map<Node, Integer> distanceMap = new HashMap<>();

        Iterator<Node> nodeIt = graph.nodeIterator();
        while (nodeIt.hasNext()) {
            Node node = nodeIt.next();
 
            // Initialize parentMap by setting all nodes' parents as the startNode. 
            distanceMap.put(node, Integer.MAX_VALUE);
        }

        distanceMap.put(startNode, 0);
        startNode.setDistance(0);
        distancePq.add(startNode);
        while (!distancePq.isEmpty()) {
            Node node = distancePq.poll();
            visitedSet.add(node);

            int newDistance = distanceMap.get(node) + 1; // Since it's an unweighted graph, the new distance will be the same for others if they are use this node as
            Iterator<Edge> edgeIt = graph.edgeIterator(node);
            while (edgeIt.hasNext()) {
                Node neighbor = edgeIt.next().getDest();
                if (!visitedSet.contains(neighbor)) {
                    int currentDistance = distanceMap.get(neighbor);
                    
                    // If distance == Integer.MAX_VALUE, then it's not inside priority queue. 
                    // Just update the distance map and parent.
                    // Then add the neighbor to the priority queue.
                    if (currentDistance == Integer.MAX_VALUE) {
                        distanceMap.put(neighbor, newDistance);
                        parentMap.put(neighbor, node);
                        
                        neighbor.setDistance(distanceMap.get(neighbor));
                        distancePq.add(neighbor);
                    }

                    // If distance != Integer.MAX_VALUE, then it is inside priority queue.
                    // Update the distance map and the parent.
                    // Then remove the current one from the queue, then update the neighbor and add it to the pq again.
                    else if (newDistance < currentDistance) {
                        distanceMap.put(neighbor, newDistance);
                        parentMap.put(neighbor, node);

                        distancePq.remove(neighbor);
                        neighbor.setDistance(distanceMap.get(neighbor));
                        distancePq.add(neighbor);
                    }
                }
            }
        }

        // Check if a feasible path is found or not by checking the distance of the end node to the start node.
        if (distanceMap.get(endNode) == Integer.MAX_VALUE) {
            throw new Exception("No feasible path is found!");
        }

        List<Node> result = new ArrayList<>();
        Node curNode = endNode;
        while (!curNode.equals(startNode)) {
            result.add(curNode);
            curNode = parentMap.get(curNode);
        }
        result.add(startNode);
        length = result.size() - 1;
        return result;
    }
}

/**
 * Comparator to compare the nodes according to their distances.
 */
class NodeComparatorDistance implements Comparator<Node> {
    public int compare(Node node1, Node node2) {
        if (node1.getDistance() < node2.getDistance()) return -1;
        if (node1.getDistance() > node2.getDistance()) return 1;
        return 0;
    }
}

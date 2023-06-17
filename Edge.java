/**
 * The edge class that represent an edge in the graph.
 */
public class Edge {
    private Node source;
    private Node dest;

    /**
     * The constructor that initializes the edge with the given source and destination.
     * @param source The source node.
     * @param dest The destination node.
     */
    public Edge(Node source, Node dest) {
        this.source = source;
        this.dest = dest;
    }

    /**
     * Gets the source node.
     * @return The source node.
     */
    public Node getSource() {
        return source;
    }

    /**
     * Gets the destination node.
     * @return The destination node.
     */
    public Node getDest() {
        return dest;
    }

    /**
     * The equals method overridden to compare with the given object properly.
     * @return A boolean indicating if the given object/edge is equal to this node or not.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (object.getClass() != Edge.class) return false;
        Edge oth = (Edge) object;
        if (source.equals(oth.source) && dest.equals(oth.dest)) return true;
        return false;
    }

    /**
     * The hashCode method overriden to return a proper hashCode.
     * @return An integer representing the hash code.
     */
    @Override
    public int hashCode() {
        return source.hashCode() * 11 + dest.hashCode() * 131 + 13;
    }
}

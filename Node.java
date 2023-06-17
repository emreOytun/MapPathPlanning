/**
 * This class keeps a node's informations such as coordinates and its distance from a specific node.
 */
public class Node {
    private int x;
    private int y;
    private int distance;

    /**
     * Constructor to initialize the node with the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The constructor initialize the node with the given coordinates and the given distance.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param distance The distance of the node to a specific node.
     */
    public Node(int x, int y, int distance) {
        this(x, y);
        this.distance = distance;
    }

    /**
     * Gets the distance.
     * @return An integer representing the distance.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the distance.
     * @param distance The distance that will be assigned to.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Gets X coordinate.
     * @return An integer representing the X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets Y coordinate.
     * @return An integer representing the Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * The equals method to compare with the given object/node.
     * @return A boolean indicating if the given object and this node are equal or not.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (object.getClass() != Node.class) return false;
        Node oth = (Node) object;
        if (x == oth.x && y == oth.y) return true;
        return false;
    }

    /**
     * The hashCode method that returns the hash code of the node.
     * @return An integer representing the hash code.
     */
    @Override
    public int hashCode() {
        return x*13 + y*131 + 11;
    }

}
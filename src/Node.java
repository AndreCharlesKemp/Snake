/***
 * Node class for constructing a linked list, primarily for creating, scaling and storing the
 * snake.
 */

public class Node {
    // A circle object for various uses
    private Circle shape;

    // The next node in the linked list
    private Node next;

    // The 'tag' associated with this node for later checking (e.g "red wafer")
    private String tag;

    /***
     * Default constructor of the node, giving default values of the instance variables.
     */
    public Node() {
        tag = "Default";
        next = null;
    }

    /***
     * Allows construction of a node with a given Circle object.
     * @param s The new Circle object.
     */
    public Node(Circle s) {
        this();
        shape = s;
    }

    /***
     * Allows construction of a node with the given Circle object, and the next node in the
     * linked list.
     * @param s The new Circle object.
     * @param n The next node in the linked list.
     */
    public Node(Circle s, Node n) {
        this();
        shape = s;
        next = n;
    }

    /***
     * Sets the tag of the node.
     * @param newTag The node's new tag.
     */
    public void setTag(String s) {
        tag = s;
    }

    /***
     * Sets the Circle of the node.
     * @param newTag The node's new Circle.
     */
    public void setCircle(Circle s) {
        shape = s;
    }

    /***
     * Sets the next node of the node.
     * @param newTag The node's new next node.
     */
    public void setNext(Node n) {
        next = n;
    }

    /***
     * Gets the tag of the node.
     */
    public String getTag() {
        return tag;
    }

    /***
     * Gets the Circle of the node.
     */
    public Circle getCircle() {
        return shape;
    }

    /***
     * Gets the next node of the node.
     */
    public Node getNext() {
        return next;
    }
}
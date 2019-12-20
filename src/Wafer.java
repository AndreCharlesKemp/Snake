import java.awt.Color;

/***
 * Wafers extends Entity
 * Function as a way for the snake to grow or shrink
 * Depending on which kind is eaten -- this affect is tracked by the boolean red.
 * If red is true then the wafer shrinks the snake, green it grows the snake (this is handled in the Game class).
 */

public class Wafer extends Entity {

    // Whether the wafer is red or not
    private boolean red;

    /***
     * Default constructor of a Wafer, red defaults to false.
     */
    public Wafer() {
        Circle waferObj = new Circle(0, 0, 10);
        waferObj.setColor(Color.green);
        head = new Node(waferObj);
        head.setTag("green wafer");
    }

    /***
     * Initializes Wafer with the given red value.
     * @param isRed Whether the Wafer should be red.
     */
    public Wafer(boolean isRed) {
        this();
        red = isRed;
        if (isRed) {
            head.getCircle().setColor(Color.red);
            head.setTag("red wafer");
        }
    }

    /***
     * Initializes Wafer with the given red value at point (x, y).
     * @param isRed Whether the Wafer should be red.
     * @param x Starting x coordinate of the Wafer.
     * @param y Starting y coordinate of the Wafer.
     */
    public Wafer(boolean isRed, int x, int y) {
        this(isRed);
        head.getCircle().setX(x);
        head.getCircle().setY(y);
    }

    /***
     * Gets the Node object inherited by Entity.
     * @return The Node object.
     */
    public Node getWafer() {
        return head;
    }
}

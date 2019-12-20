import java.awt.Color;
import java.awt.Graphics;

/***
 * Since the only shape we will be using in the game is a Circle, we only need a Circle class.
 * This handles all Circle related activities, such as drawing, updating, and diameter.
 */

public class Circle {

    // The circle's top left coordinate is (x, y) and radius
    private int x = 0, y = 0, radius = 10;

    // The circle's color
    private Color color;

    /***
     * Initializes a basic circle.
     */
    public Circle() {
    }

    /***
     * Initializes a circle at (newX, newY)
     * @param newX Start x-coordinate.
     * @param newY Start y-coordinate.
     */
    public Circle(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /***
     * Initializes a circle at (newX, newY), with radius newRadius
     * @param newX Start x-coordinate.
     * @param newY Start y-coordinate.
     * @param newRadius Radius of the circle.
     */
    public Circle(int newX, int newY, int newRadius) {
        this(newX, newY);
        radius = newRadius;
    }

    /***
     * Sets the x-coordinate of the circle.
     * @param newX New x-coordinate.
     */
    public void setX(int newX) {
        x = newX;
    }

    /***
     * Sets the y-coordinate of the circle.
     * @param newY New y-coordinate.
     */
    public void setY(int newY) {
        y = newY;
    }

    /***
     * Sets the radius of the circle.
     * @param newRadius New radius.
     */
    public void setRadius(int newRadius) {
        radius = newRadius;
    }

    /***
     * Sets the color of the circle.
     * @param newColor New color.
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /***
     * Gets the x-coordinate of the circle.
     */
    public int getX() {
        return x;
    }

    /***
     * Gets the y-coordinate of the circle.
     */
    public int getY() {
        return y;
    }

    /***
     * Gets the radius of the circle.
     */
    public int getRadius() {
        return radius;
    }

    /***
     * Paints the circle to the pane.
     * @param g The graphics pane.
     */
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }
}

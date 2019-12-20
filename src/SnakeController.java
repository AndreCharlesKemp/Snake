import java.awt.Color;
import java.util.Random;

/***
 * This is the class that controls the snake in the game. It grows, shrinks, checks the collision
 * of, gets the areas it occupies, and of course updates. The drawing is performed by Entity.
 */

public class SnakeController extends Entity {
    // The direction the head of the snake is travelling
    private char direction = 'n';

    /***
     * The default constructor for the snake, initializing its controls, size, and head.
     */
    public SnakeController() {
        super();
        super.setCommands(new String[]{//"g", "grow", "f", "shrink",
                "w", "move", "a", "move", "s", "move", "d", "move"});
        super.setUser(this.getClass());
        Segment headSeg = new Segment();
        headSeg.setColor(new Color(20, 180, 0));
        head = new Node(headSeg);
    }

    /***
     * Gets the locations the segments of the snake occupy.
     * @return An array of locations the segments occupy.
     */
    public int[] getOccupiedLocations() {
        int[] locs = new int[getSnakeLength() * 2];
        Node tmp = head;
        int i = 0;
        while (tmp != null) {
            locs[i++] = tmp.getCircle().getX();
            locs[i++] = tmp.getCircle().getY();
            tmp = tmp.getNext();
        }
        return locs;
    }

    /***
     * Checks if the snake is within the screen's bounds.
     * @param w The width of the screen.
     * @param h The height of the screen.
     * @return If it is within the screen's bounds.
     */
    public boolean inBounds(int w, int h) {
        // We only have to check if the head is in bounds, since each segment
        // follows its path.
        Circle noggin = head.getCircle();

        int x = noggin.getX(), y = noggin.getY(), rad = noggin.getRadius();

        if (x + rad > w || x < 0 ||
                y < 2 * rad || y + rad > h)
            return false;

        return true;
    }

    /***
     * Checks if any segments of the snake are colliding with either each other or the
     * given array of colliders.
     * @param colliders Additional colliders to check collision against.
     * @return Whether the snake is colliding with itself (0), a green wafer (1)
     * 			or a red wafer (2)
     */
    public int checkCollision(Node[] colliders) {
        // First let's pool all of our colliders together...
        Node[] total = new Node[colliders.length + getSnakeLength()];

        // Counts total colliders added
        int j = 0;

        // First, we add all of the colliders from the parameter
        for (int i = 0; i < colliders.length; i++, j++)
            total[i] = colliders[i];

        // Now we loop through the snake and add each segment as a collider
        Node tmp = head;

        while (tmp != null) {
            total[j++] = tmp;
            tmp = tmp.getNext();
        }

        // Time to check for collision.
        Node snakeSegment = head;

        while (snakeSegment != null) {
            for (Node collider : total) {
                Circle src = snakeSegment.getCircle(),
                        tgt = collider.getCircle();

                // Since we're using a snapped grid, we can check coorinates directly.
                if (tgt.equals(src) == false &&
                        tgt.getX() == src.getX() &&
                        tgt.getY() == src.getY()) {
                    // Here we use the tag system to determine what we hit
                    if (collider.getTag().equals("green wafer"))
                        return 1;
                    else if (collider.getTag().equals("red wafer"))
                        return 2;
                    else
                        return 0;
                }
            }
            snakeSegment = snakeSegment.getNext();
        }
        return -1;
    }

    /***
     * Initiates recursive call of snake length
     * @return The length of the snake.
     */
    public int getSnakeLength() {
        return getSnakeLength(head);
    }

    /***
     * Recursively counts the length of the snake.
     * @param n The next node in the snake to count.
     * @return The count of segments thusfar.
     */
    public int getSnakeLength(Node n) {
        if (n == null)
            return 0;
        else
            return 1 + getSnakeLength(n.getNext());
    }

    /***
     * Increments the size of the snake by adding a node with a segment.
     */
    public void grow() {
        Node tmp = head;

        // Find the last node of the snake
        while (tmp.getNext() != null)
            tmp = tmp.getNext();

        // The location of the last segment. This will become the location of the new
        // segment in just a moment.
        int xLoc = tmp.getCircle().getX(),
                yLoc = tmp.getCircle().getY(),
                rad = tmp.getCircle().getRadius();

        // The last segment of the snake
        Segment tmpSeg = (Segment) tmp.getCircle();

        // Get the direction of the segment and increment in the opposite direction,
        // letting us add the new last segment directly behind the old last segment.
        char tmpDir = tmpSeg.getDirection();
        if (tmpDir == 'w')
            yLoc += rad;
        else if (tmpDir == 's')
            yLoc -= rad;
        else if (tmpDir == 'a')
            xLoc += rad;
        else if (tmpDir == 'd')
            xLoc -= rad;

        // The new last segment, created at the previous coordinate.
        Segment s = new Segment(xLoc, yLoc);

        // Give it a semi-random, mostly green, color
        Random rn = new Random();
        s.setColor(new Color(rn.nextInt(150), 240, rn.nextInt(150)));

        // Copy the old last segments orders over so this guy knows where to go.
        s.setOrders(tmpSeg.getOrders());

        // Match the old last segments direction
        s.setDirection(tmpDir);

        // Officially add him in
        tmp.setNext(new Node(s));
    }

    /***
     * Adds an order to each segment in the snake.
     * @param dir The direction of the order.
     */
    public void addSnakeOrder(char dir) {
        Node tmp = head;
        while (tmp != null) {
            Segment s = (Segment) tmp.getCircle();
            s.addOrder(head.getCircle().getX(), head.getCircle().getY(), dir);
            tmp = tmp.getNext();
        }
    }

    /***
     * Removes a node from the snake, and returns whether the snake is dead.
     */
    public void shrink() {
        Node tmp = head;

        // Find the last segment
        while (tmp.getNext().getNext() != null)
            tmp = tmp.getNext();

        // and nullify it
        tmp.setNext(null);
    }

    /***
     * Changes the direction of the snake to the given direction. This is invoked via reflection.
     * @param dir The new direction of the snake.
     */
    public void move(String dir) {

        char cDir = dir.charAt(0);

        if (cDir != direction) {
            // Check if we are attempting to move in an opposite direction...
            switch (cDir) {
                case 'w':
                    if (direction == 's')
                        return;
                    break;
                case 's':
                    if (direction == 'w')
                        return;
                    break;
                case 'a':
                    if (direction == 'd')
                        return;
                    break;
                case 'd':
                    if (direction == 'a')
                        return;
                    break;
            }
            // If not, then set the new direction and let everyone know about it
            direction = cDir;
            addSnakeOrder(cDir);
        }
    }

    /***
     * Updates the nodes in the snake.
     */
    public void update() {
        Node tmp = head;
        while (tmp != null) {
            Segment s = (Segment) tmp.getCircle();
            s.update();

            // The segments move equal to their radius
            int rad = s.getRadius();

            switch (s.getDirection()) {
                case 'w':
                    s.setY(s.getY() - rad);
                    break;
                case 's':
                    s.setY(s.getY() + rad);
                    break;
                case 'd':
                    s.setX(s.getX() + rad);
                    break;
                case 'a':
                    s.setX(s.getX() - rad);
            }
            tmp = tmp.getNext();
        }
    }
}

/***
 * Segment subclass serves to provide a complete unit for building the snake. Each
 * segment can record orders, and follow the head of the snake's path around the level.
 * Not embedded in the SnakeController class for greater readability.
 */

class Segment extends Circle {
    /***
     * Orders indicate where to turn the snake, updated upon every keypress
     */
    private class Order {
        // (x, y) location of the turn
        public int x, y;

        // direction of the turn
        public char direction = 'n';

        /***
         * Constructor for easily initializing an order.
         * @param newX x location of the turn
         * @param newY y location of the turn
         * @param newDirection direction of the turn
         */
        public Order(int newX, int newY, char newDirection) {
            x = newX;
            y = newY;
            direction = newDirection;
        }
    }

    // Direction the segment is heading
    private char direction = 'n';

    // Array of orders for the segment
    private Order[] orders = new Order[256];

    /***
     * Constructor for initializing the segment
     */
    public Segment() {
        super(190, 190, 10);
    }

    /***
     * Constructor for initializing a segment at position (x, y)
     * @param newX Starting x of the segment
     * @param newY Starting y of the segment
     */
    public Segment(int newX, int newY) {
        super(newX, newY);
    }

    /***
     * Adds an order to the segment
     * @param x x-location of the turn
     * @param y y-location of the turn
     * @param dir Direction of the turn
     */
    public void addOrder(int x, int y, char dir) {
        // Find a null order and replace it with the desired one
        for (int i = 0; i < orders.length; i++) {
            if (orders[i] == null) {
                orders[i] = new Order(x, y, dir);
                return;
            }
        }
    }

    /***
     * Updates the segment, checking if any orders need execution.
     */
    public void update() {
        for (int i = 0; i < orders.length; i++) {
            // execute any valid order
            Order o = orders[i];
            if (o != null && getX() == o.x && getY() == o.y) {
                direction = o.direction;
                orders[i] = null;
            }
        }
    }

    /***
     * Gets the array of orders that the segment has.
     * @return The array of orders
     */
    public Order[] getOrders() {
        return orders;
    }

    /***
     * Sets the segment's orders.
     * @param newOrders The segment's new orders.
     */
    public void setOrders(Order[] newOrders) {
        for (Order order : newOrders) {
            if (order != null && order.direction != 'n')
                addOrder(order.x, order.y, order.direction);
        }
    }

    /***
     * Sets the direction of the segment.
     * @param d The new direction
     */
    public void setDirection(char d) {
        direction = d;
    }

    /***
     * Gets the direction of the segment.
     * @return The segment's direction.
     */
    public char getDirection() {
        return direction;
    }
}

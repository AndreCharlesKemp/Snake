import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***
 * Menu class which will serve as a base for all other menus in the game, performing mundane
 * functions of drawing and updating.
 */

public class Menu implements MouseListener {
    // A list of buttons that will be drawn
    private Abutton[] buttons;

    // This keeps track of the last button event
    private String lastEvent;

    // The title of the menu, displayed at the top of its frame
    private String title;

    // Top left of the frame is (x, y)
    private int x, y;

    // Bottom right of the frame is (width, height)
    private int width, height;

    // Determines if we should keep the menu open.
    private boolean open;

    /***
     * The default constructor, initializes everything.
     */
    public Menu() {
        title = "Main";
        x = y = 20;
        width = height = 200;
        buttons = new Abutton[]{generateButton("test1", Color.gray, 40, 40), generateButton("exit", Color.gray, 180, 20)};
        open = false;
    }

    /***
     * The constructor to be used elsewhere, creates a unique interface through the parameters.
     * @param newTitle The menu's new title.
     * @param buttons The menu's new list of buttons.
     * @param newX The new top left x coordinate.
     * @param newY The new top left y coordinate.
     * @param newWidth The new bottom right x coordinate / width.
     * @param newHeight The new bottom right y coordinate / height.
     */
    public Menu(String newTitle, int newX, int newY, int newWidth, int newHeight) {
        this();
        title = newTitle;
        x = newX;
        y = newY;
        width = newWidth;
        height = newHeight;
    }

    /***
     * Set the array of buttons to another array of buttons.
     * @param newButtons The new array of buttons.
     */
    public void setButtons(Abutton[] newButtons) {
        buttons = newButtons;
    }

    /***
     * Generates and returns a new button.
     * @param name Label of the button.
     * @param color Color of the button.
     * @param x Starting x-coordinate.
     * @param y Starting y-coordinate.
     * @return The new button.
     */
    public Abutton generateButton(String name, Color color, int x, int y) {
        return new Abutton(name, color, x, y);
    }

    /***
     * Gets the latest button event.
     * @return Latest button event.
     */
    public String getEvent() {
        String evt = lastEvent;
        lastEvent = null;
        return evt == null ? "" : evt;
    }

    /***
     * Returns whether we should keep this menu alive.
     * @return True if we should keep it alive.
     */
    public boolean isOpen() {
        return open;
    }

    /***
     * Updates all of the buttons.
     * @param someX Mouse event x coordinate.
     * @param someY Mouse event y coordinate.
     */
    public void update(int someX, int someY) {
        for (int i = 0; i < buttons.length; i++) {
            // Have we clicked a button? Then save its label to lastEvent
            if (buttons[i].isInside(someX, someY))
                lastEvent = buttons[i].getLabel();
        }
    }

    /***
     * Draws all of the items in the menu.
     * @param g The pane to drawn onto.
     */
    public void paint(Graphics g) {
        // Clear the frame for fresh painting
        g.clearRect(0, 0, width, height);

        // Save the font
        Font tmp = g.getFont();

        // Draw the menu title in the bottom right
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        g.drawString(title, width - title.length() * 6, height - 6);

        // Return to normal font
        g.setFont(tmp);

        // Paint what your children tell you to.
        paintAdditional(g);

        // Paint the buttons
        for (int i = 0; i < buttons.length; i++)
            buttons[i].paint(g);
    }

    /***
     * Method to be overriden with additional elements for drawing.
     * @param g The pane to be drawn onto.
     */
    protected void paintAdditional(Graphics g) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        update(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}

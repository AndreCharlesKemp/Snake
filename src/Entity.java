import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/***
 * The Entity class sets the groundwork for a dynamic linked list in the game by using reflection
 * and performs the functions of painting and updating its members.
 */

public class Entity {
    // Class to use reflection on
    private Class user;

    // Commands correspond to method names
    private String[] commands;

    // (x, y) coordinate of the entity
    private int x, y;

    // the head of the entity's linked list
    public Node head;

    /***
     * Initializes a null entity.
     */
    public Entity() {
        user = null;
        commands = null;
        head = null;
        x = y = 0;
    }

    /***
     * Set the user class for reflection.
     * @param c The new user class.
     */
    public void setUser(Class newUser) {
        user = newUser;
    }

    /***
     * Set new commands for the entity.
     * @param newCommands The new command list.
     */
    public void setCommands(String[] newCommands) {
        commands = newCommands;
    }

    /***
     * Executes a command if it corresponds to a keypress. This method uses reflection to
     * make movement a "fire and forget" function of entities.
     * @param event The key pressed.
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public void keyPressed(KeyEvent event) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
        char key = event.getKeyChar();

        // Run through each command in the set, skipping over the method names
        for (int i = 0; i < commands.length; i += 2) {
            // If the pressed key corresponds to a command...
            if (key == commands[i].charAt(0)) {
                // Get the corresponding method...
                Method method = user.getDeclaredMethod(commands[i + 1], String.class);
                try {
                    // and invoke it.
                    method.invoke(this, commands[i]);
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    // Otherwise, tell us where we went wrong.
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * Paints each object in this entity's linked list.
     * @param g The graphics pane.
     */
    public void paint(Graphics g) {
        Node next = head;
        while (next != null) {
            next.getCircle().paint(g);
            next = next.getNext();
        }
    }
}

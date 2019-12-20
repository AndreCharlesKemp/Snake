/***
 * Programmer: Andre Charles Kemp
 *
 * Snake
 *
 * This will be an implementation of the classic game Snake with linked lists, sorting, and
 * collision detection. See the project description for greater detail.
 */

public class main {
    // The global game object
    private static Game global;

    public static void main(String[] args) {
        global = new Game();
        global.update();
    }
}

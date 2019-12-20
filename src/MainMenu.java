import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/***
 * The Main Menu is where the user starts and ends the program, and the game.
 * It allows access to the other menus.
 */
public class MainMenu extends Menu {
    // The random subtitle
    String sub;

    /***
     * Initializes the main menu. Everything else is taken care of by Menu and in Game.
     * @param winX Window starting x coordinate.
     * @param winY Window starting y coordinate.
     * @param winWidth Window ending x coordinate.
     * @param winHeight Window ending y coordinate.
     */
    public MainMenu(int winX, int winY, int winWidth, int winHeight) {
        super("Snake", winX, winY, winWidth, winHeight);
        setButtons(new Abutton[]{generateButton("New Game", Color.white, 155, 200),
                generateButton("Highscores", Color.white, 155, 235),
                generateButton("Quit", Color.white, 155, 270)});

        // Possible subtitles...
        String[] randomSubs = {"Add me on LinkedIn", "Snakes on a Plane Edition",
                "Now with Highscores", "The Linked List Edition", "A Clever Python Pun"};

        // Choose a random one.
        sub = randomSubs[new Random().nextInt(randomSubs.length)];
    }

    /***
     * Paints the game title and subtitle.
     */
    protected void paintAdditional(Graphics g) {
        // Save font
        Font tmp = g.getFont();

        // Title string
        g.setColor(new Color(20, 130, 0));
        g.setFont(new Font("Courier", Font.PLAIN, 30));
        g.drawString("Snake", 145, 150);

        // Return to normal font, paint the subtitle
        g.setFont(tmp);
        g.drawString(sub, 140 - sub.length() / 2, 180);
    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class GameOverMenu extends Menu {
    // The player's total score
    int score;

    /***
     * Initializes the menu and lets it know what the player's stats were in their game.
     * @param winX Window starting x coordinate.
     * @param winY Window starting y coordinate.
     * @param winWidth Window ending x coordinate.
     * @param winHeight Window ending y coordinate.
     * @param newScore Player's score.
     */
    public GameOverMenu(int winX, int winY, int winWidth, int winHeight, int score) {
        super("", winX, winY, winWidth, winHeight);
        this.score = score;
        setButtons(new Abutton[]{generateButton("New Game", Color.white, 155, 200),
                generateButton("Return", Color.white, 155, 230)});
    }

    /***
     * This will paint the player's score on the menu.
     */
    protected void paintAdditional(Graphics g) {
        // Save font
        Font tmp = g.getFont();

        // Really let them know they have failed
        g.setColor(Color.red);
        g.setFont(new Font("Helvetica", Font.BOLD, 32));
        g.drawString("Game Over", 110, 150);

        // Return to normal font
        g.setFont(tmp);
        g.drawString("Final Score: " + score, 150, 180);
    }
}

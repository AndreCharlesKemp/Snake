import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/***
 * The high scores menu will interact with a text file to get high scores, sort them, and display
 * them. Otherwise it's a normal menu.
 */

public class HighscoresMenu extends Menu {
    private String[] scores = new String[256];

    /***
     * Constructor for initializing the menu.
     * @param winX Window starting x coordinate.
     * @param winY Window starting y coordinate.
     * @param winWidth Window ending x coordinate.
     * @param winHeight Window ending y coordinate.
     */
    public HighscoresMenu(int winX, int winY, int winWidth, int winHeight) {
        super("Highscores Menu", winX, winY, winWidth, winHeight);
        setButtons(new Abutton[]{generateButton("Return", Color.white, 140, 350)});
    }

    /***
     * Opens text file and copies contents to scores array.
     * @throws IOException
     */
    public void getScores() throws IOException {
        BufferedReader txt = new BufferedReader(new FileReader("./scores"));
        for (int i = 0; i < scores.length; i++)
            scores[i] = txt.readLine();
        sort();
        txt.close();
    }

    /***
     * Simple bubble sort of the scores.
     */
    private void sort() {
        // Standard bubble sort algorithm
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < scores.length; i++) {
                if (valid(scores[i]) && valid(scores[i + 1]) &&
                        Integer.parseInt(scores[i]) < Integer.parseInt(scores[i + 1])) {
                    String p = scores[i];
                    scores[i] = scores[i + 1];
                    scores[i + 1] = p;
                    sorted = false;
                }
            }
        }

        // Starting after the 10th element, delete the remaining values.
        // These are extraneous, since only the top 10 are ever displayed.
        for (int i = 10; i < scores.length; i++)
            scores[i] = null;

        // Rewrite the scores document to have only the 10 highest scores.
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("scores"));
            for (int i = 0; i < 10; i++)
                if (valid(scores[i]))
                    out.write(scores[i] + "\n");
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e2) {
            }
        }
    }

    /***
     * Determines whether the given string is actually a score.
     * @param s The string to test.
     * @return True if the string is a score.
     */
    private boolean valid(String s) {
        return (s != null && !s.isEmpty() && s != "???");
    }

    /***
     * Will be used to draw the score strings onto the menu.
     */
    protected void paintAdditional(Graphics g) {
        // Save font
        Font tmp = g.getFont();

        // Bask in their glory
        g.setColor(Color.blue);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("The Highest Scores Ever Scored", 35, 120);

        // Return to normal color & font
        g.setColor(Color.black);
        g.setFont(tmp);

        // Draw the scores
        for (int i = 0; i < 10; i++) {
            // Any null scores are displayed as ???
            if (scores[i] == null)
                scores[i] = "???";
            g.drawString(i + 1 + ". " + scores[i], 145, 150 + i * 21);
        }
    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/***
 * The workhorse class of the program.
 * Class serves two roles:
 * 		- It controls the actual game of Snake itself
 * 		- Transitions between the various menus in the game.
 */

public class Game extends Frame implements KeyListener, AlarmListener {
    // The snake in the game.
    private SnakeController snake;

    // The current menu we are displaying; could be GameOver, High Scores or Main Menu.
    private Menu menu;

    // The red and green wafers in the game.
    private Wafer currentRed, currentGreen;

    // The controller of how many frames we draw in the game.
    private Alarm fps;

    // The trackers of score and consumed wafers
    private int score, redWafers, greenWafers;

    // Determines if we're showing a menu or not.
    private boolean play;

    /***
     * Constructor that initializes everything.
     */
    public Game() {
        super(" *Snake* ");

        // Basic pregame startup
        play = false;
        redWafers = greenWafers = score = 0;
        snake = new SnakeController();
        newMenu(new MainMenu(0, 0, getWidth(), getHeight()));

        // Frame & input setup
        addMouseListener(menu);
        addKeyListener(this);
        setLayout(null);
        setSize(400, 400);
        setBackground(Color.black);
        setResizable(false);
        setVisible(true);
        getGraphics().setFont(new Font("Helvetica", Font.PLAIN, 10));

        // Alarm listener for FPS management
        fps = new Alarm(this);
        fps.setPeriod(60);
        fps.start();
    }

    /***
     * Generates a red or green wafer somewhere in the level.
     * @param red Whether the wafer should be red or not.
     * @return The generated wafer.
     */
    public Wafer generateWafer(boolean red) {
        // Holds location (x,y) of our future wafer
        int[] loc = new int[2];

        // Used to determine if we're generating the wafer in a valid spot.
        boolean valid = false;

        while (!valid) {
            valid = true;

            // Generates a random coordinate within the level's bounds
            Random rn = new Random();
            loc = new int[]{10 + (rn.nextInt(37)) * 10, 20 +
                    (rn.nextInt(37)) * 10};

            // Check if the generated location is occupied by the snake
            int[] invalids = snake.getOccupiedLocations();
            for (int i : invalids)
                if (i == (loc[0] | loc[1]))
                    valid = false;
        }

        return new Wafer(red, loc[0], loc[1]);
    }

    /***
     * Updates everything that is active.
     */
    public void update() {
        if (play)
            game();

        if (menu != null) {
            // Check what button has been pressed in our menu (if any) and perform
            // the corresponding action.
            switch (menu.getEvent()) {
                case "New Game":
                    newGame();
                    break;
                case "Highscores":
                    newMenu(new HighscoresMenu(0, 0, getWidth(), getHeight()));
                    try {
                        ((HighscoresMenu) menu).getScores();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Quit":
                    System.exit(0);
                    break;
                case "Return":
                    newMenu(new MainMenu(0, 0, getWidth(), getHeight()));
                    break;
            }
        }
    }

    /***
     * Handles the various components of the game:
     * 		- Updating the snake
     * 		- Checking if the player has lost, and if they've eaten a wafer.
     */
    private void game() {
        // Game Over if the snake is outside the level's bounds
        if (!snake.inBounds(getWidth(), getHeight()))
            gameOver();

        // Get what the snake has collided with (if anything)
        int result = snake.checkCollision(new Node[]{currentGreen.getWafer(), currentRed.getWafer()});

        // Check what the output was
        switch (result) {
            // Self collision, game over.
            case 0:
                gameOver();
                break;
            // Green wafer, grow and increase the score and game speed (if applicable)
            case 1:
                currentGreen = generateWafer(false);
                score += 10;
                greenWafers++;
                snake.grow();

                // Increases the game speed to a maximum of 1 millisecond delay
                if (score > 60 && score < 3660)
                    if ((score / 10) % 6 == 0)
                        fps.setPeriod(60 - (score / 10 - 6) / 6);
                break;
            // Red wafer, shrink, increase the score and check if they've lost
            case 2:
                currentRed = generateWafer(true);
                score += 5;
                redWafers++;
                if (snake.getSnakeLength() == 1)
                    gameOver();
                else
                    snake.shrink();
        }
        snake.update();
    }

    /***
     * Bind the menu to the new desired form.
     * Add the mouse listener to it.
     * @param tmp The new menu form.
     */
    public void newMenu(Menu tmp) {
        menu = tmp;
        addMouseListener(menu);
    }

    /***
     * Wipes the menu and trackers, and generates a fresh snake and wafers.
     */
    public void newGame() {
        menu = null;
        play = true;
        score = redWafers = greenWafers = 0;
        snake = new SnakeController();
        currentRed = generateWafer(true);
        currentGreen = generateWafer(false);
    }

    /***
     * Called when the player loses the game.
     * Disables the game and pops up the Game Over menu.
     */
    public void gameOver() {
        // See User Manual: Scoring
        score *= greenWafers / (redWafers + 1);

        // No longer playing, generate a game over menu
        play = false;
        newMenu(new GameOverMenu(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2, score));

        // Append the final score to the scores file
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("./scores", true));
            out.write(score + "\n");
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
     * Paints everything that is active.
     */
    public void paint(Graphics g) {
        if (play) {
            snake.paint(g);
            currentRed.paint(g);
            currentGreen.paint(g);

            // Colorizes and draws the trackers
            g.setColor(Color.black);
            g.drawString("Score: " + score, 0, 32);

            // Darkish green. Standard Color.green is hard to read...
            g.setColor(new Color(82, 220, 28));
            g.drawString("Green Wafers: " + greenWafers, 0, 46);

            g.setColor(Color.red);
            g.drawString("Red Wafers: " + redWafers, 0, 60);

            g.setColor(Color.black);
            g.drawString("Length: " + snake.getSnakeLength(), 0, 74);

            // Reset the canvas color
            g.setColor(Color.white);
        }

        if (menu != null)
            menu.paint(g);

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        try {
            try {
                // Sends the key even to the snake for processing
                snake.keyPressed(e);
            } catch (NoSuchMethodException | SecurityException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // If the player presses Escape in a game, it's game over.
        if (play && e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gameOver();

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void takeNotice() {
        // Update the game
        update();
    }
}

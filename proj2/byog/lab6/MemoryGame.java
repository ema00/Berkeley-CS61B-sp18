package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;



public class MemoryGame {

    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    /* Time, in milliseconds, to display each letter when flashing letters sequence. */
    private static int CHAR_FLASH_TIME = 500;
    /* Time, in milliseconds, between the display of each letter flashed. */
    private static int BLANK_SCREEN_FLASH_TIME = 300;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed.");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        Random random = new Random(seed);
        MemoryGame game = new MemoryGame(40, 40, random);
        game.startGame();
    }

    public MemoryGame(int width, int height, Random random) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = random;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public String generateRandomString(int n) {
        char[] characters = new char[n];
        int bound = CHARACTERS.length;
        for (int i = 0; i < n; i++) {
            characters[i] = CHARACTERS[rand.nextInt(bound)];
        }
        return new String(characters);
    }

    /**
     * Displays a String centered on the window, and the status menu of the game.
     * @param s is the String to be displayed in the window.
     */
    public void drawFrame(String s) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        if (!gameOver) {
            //TODO: If game is not over, display relevant game information at the top of the screen
            displayStatus();
            StdDraw.text(this.width / 2, this.height / 2, s);
            StdDraw.show();
        }
    }

    /**
     * Displays each character in the String, blanking the screen between characters.
     * @param letters is the String whose characters are to be displayed.
     */
    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(CHAR_FLASH_TIME);
            drawFrame("");
            StdDraw.pause(BLANK_SCREEN_FLASH_TIME);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        return null;
    }

    public void startGame() {

        // THIS IS JUST FOR TESTING THE METHODS AS THEY ARE WRITTEN
        String s = generateRandomString(6);
        flashSequence(s);

        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Game loop
    }

    /**
     * Adds the status menu elements to display at any given moment of the game (when not over).
     */
    private void displayStatus() {
    }

}

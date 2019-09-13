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
    private static final int CHAR_FLASH_TIME = 500;
    /* Time, in milliseconds, between the display of each letter flashed. */
    private static final int BLANK_SCREEN_FLASH_TIME = 300;
    /* Time, in milliseconds, to wait between entered letters reading. */
    private static final int LETTER_SCAN_TIME = 200;
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

    /**
     * Plays the memory game.
     */
    public void startGame() {
        round = 0;
        while (!gameOver) {
            round += 1;
            displayRound(round);
            String string = generateRandomString(round);
            flashSequence(string);
            String answer = solicitNCharsInput(round);
            gameOver = !string.equals(answer);
        }
        drawFrame("Game Over! You made it to round " + round + ".");
    }

    /**
     * Generates a random String of lowercase letters.
     * @param n is the length of the String to generate.
     * @return a String of random letters, that may be repeated, from the CHARACTERS String.
     */
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
        displayStatus();
        StdDraw.text(this.width / 2, this.height / 2, s);
        StdDraw.show();
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
        StdDraw.pause(CHAR_FLASH_TIME);
    }

    /**
     * Reads n letters of player input, and displays the letters on the game window.
     * @param n is the number of letters to read from the keyboard.
     * @return the letters entered by the user as a lowercase String.
     */
    public String solicitNCharsInput(int n) {
        char[] letters = new char[n];
        for (int i = 0; i < n; i++) {
            while (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(LETTER_SCAN_TIME);
            }
            letters[i] = StdDraw.nextKeyTyped();
            drawFrame((new String(letters)).toLowerCase());
        }
        StdDraw.pause(2 * CHAR_FLASH_TIME);
        return (new String(letters)).toLowerCase();
    }

    /**
     * Adds the status menu elements to display at any given moment of the game (when not over).
     */
    private void displayStatus() {
    }

    /**
     * Displays the roudn number, and makes a pause.
     * @param round is the round number to be displayed to the player.
     */
    private void displayRound(int round) {
        drawFrame("Round: " + round);
        StdDraw.pause(2 * CHAR_FLASH_TIME);
        drawFrame("");
        StdDraw.pause(2 * CHAR_FLASH_TIME);
    }

}

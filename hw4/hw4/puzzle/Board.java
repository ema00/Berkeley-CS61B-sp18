package hw4.puzzle;

import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;



/**
 * Board
 *
 * @author Emanuel Aguirre
 */
public class Board implements WorldState {

    private static final int BLANK = 0;

    private final int[][] tiles;
    private final int[][] goal;
    private final int N;


    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = cloneState(tiles);
        goal = calculateSolution(N);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        if (this.N != ((Board) o).N) {
            return false;
        }

        Board other = (Board) o;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (tiles != null) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    result += tiles[i][j] * (i + j * N);
                }
            }
        }
        if (goal != null) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    result += goal[i][j] * (i + j * N);
                }
            }
        }
        return result;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    /**
     * THIS IS THE IMPLEMENTATION OF CS61B, IT HAS TO BE CHANGED TO MY IMPLEMENTATION.
     * @code Josh Hug (from Berkeley's CS61B @ http://joshh.ug/neighbors.html)
     */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i >= N || j >= N) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    public int hamming() {
        return 0;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int number = tiles[i][j];
                if (number != BLANK) {
                    int row = rowInGoal(number);
                    int col = colInGoal(number);
                    result += manhattan(i, j, row, col);
                }
            }
        }
        return result;
    }

    private int manhattan(int row1, int col1, int row2, int col2) {
        return Math.abs(row2 - row1) + Math.abs(col2 - col1);
    }

    private int rowInGoal(int number) {
        return number == BLANK ? (N - 1) : (number - 1) / N;
    }

    private int colInGoal(int number) {
        return number == BLANK ? (N - 1) : (number - 1) % N;
    }

    private int[][] calculateSolution(int n) {
        int[][] solution = new int[n][n];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                solution[i][j] = i + j * N + 1;
            }
        }
        solution[n - 1][n - 1] = BLANK;
        return solution;
    }

    /**
     * Clones the state of the tiles of the board.
     * Precondition: size of the tiles to clone must be the same as the one from this.
     * @return a clone of the tiles passed as parameter.
     */
    private int[][] cloneState(int[][] source) {
        int[][] tiles = new int[N][];
        for (int i = 0; i < N; i++) {
            tiles[i] = Arrays.copyOf(source[i], N);
        }
        return tiles;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}

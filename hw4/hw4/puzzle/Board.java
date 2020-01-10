package hw4.puzzle;

import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;



/**
 * CS61B Homework 4: https://sp18.datastructur.es/materials/hw/hw4/hw4
 *
 * Board
 *
 * Implements an N-puzzle (in an NxN board) to be solved by the Solver class.
 * This puzzle is an (N^2 - 1) version of the 8-puzzle, which is a puzzle invented
 * by Noyes Palmer Chapman in the 1870s, etc., etc., as described in Homework 4.
 *
 * @author Emanuel Aguirre
 */
public class Board implements WorldState {

    private static final int BLANK = 0;

    private final int[][] tiles;
    private final int N;


    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = cloneState(tiles);
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
        return result;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    /**
     * Returns the neighbor states of the current state of the board. That is, the
     * states that are one move away from the current state of the board. Those are
     * obtained by swapping the BLANK tile by each of its (2, 3 or 4) neighbors.
     */
    public Iterable<WorldState> neighbors() {
        final int x = 0;
        final int y = 1;
        Queue<WorldState> neighbors = new Queue<>();
        int[] blankTilePosition = getBlankTilePosition();
        int row = blankTilePosition[x];
        int col = blankTilePosition[y];

        if (row - 1 >= 0) {             // top neighbor
            Board neighbor = new Board(this.tiles);
            neighbor.setTileAt(row, col, this.tileAt(row - 1, col));
            neighbor.setTileAt(row - 1, col, BLANK);
            neighbors.enqueue(neighbor);
        }
        if (row + 1 < N) {              // bottom neighbor
            Board neighbor = new Board(this.tiles);
            neighbor.setTileAt(row, col, this.tileAt(row + 1, col));
            neighbor.setTileAt(row + 1, col, BLANK);
            neighbors.enqueue(neighbor);
        }
        if (col - 1 >= 0) {             // left neighbor
            Board neighbor = new Board(this.tiles);
            neighbor.setTileAt(row, col, this.tileAt(row, col - 1));
            neighbor.setTileAt(row, col - 1, BLANK);
            neighbors.enqueue(neighbor);
        }
        if (col + 1 < N) {              // right neighbor
            Board neighbor = new Board(this.tiles);
            neighbor.setTileAt(row, col, this.tileAt(row, col + 1));
            neighbor.setTileAt(row, col + 1, BLANK);
            neighbors.enqueue(neighbor);
        }
        return neighbors;
    }

    /**
     * Returns the position of the blank tile in the board.
     * Precondition: the BLANK tile (number 0) has to be in the board (tiles).
     * @return the position of the blank tile in the board.
     */
    private int[] getBlankTilePosition() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int number = tiles[i][j];
                if (number == BLANK) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    void setTileAt(int i, int j, int number) {
        if (i < 0 || j < 0 || i >= N || j >= N) {
            throw new IndexOutOfBoundsException();
        }
        tiles[i][j] = number;
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

    /**
     * @return the sum of the manhattan distances for all the tiles, except BLANK.
     */
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

    /**
     * Manhattan distance between the position in which a tile currently is
     * and the position in which the tile has to be in the solution.
     * @return the manhattan distance for a single tile.
     */
    private int manhattan(int row1, int col1, int row2, int col2) {
        return Math.abs(row2 - row1) + Math.abs(col2 - col1);
    }

    /**
     * @return the row in which a tile is in the solution.
     */
    private int rowInGoal(int number) {
        return number == BLANK ? (N - 1) : (number - 1) / N;
    }

    /**
     * @return the column in which a tile is in the solution.
     */
    private int colInGoal(int number) {
        return number == BLANK ? (N - 1) : (number - 1) % N;
    }

    /**
     * Clones the state of the tiles of the board.
     * Precondition: size of the tiles to clone must be the same as the one from this.
     * @return a clone of the tiles passed as parameter.
     */
    private int[][] cloneState(int[][] source) {
        int[][] clonedTiles = new int[N][];
        for (int i = 0; i < N; i++) {
            clonedTiles[i] = Arrays.copyOf(source[i], N);
        }
        return clonedTiles;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int size = size();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}

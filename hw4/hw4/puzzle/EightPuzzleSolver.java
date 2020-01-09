package hw4.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class EightPuzzleSolver {
    /***********************************************************************
     * Test routine for your Solver class. Uncomment and run to test
     * your basic functionality.
     * In case you want to test it:
     * A 2x2 example puzzle that has a solution: 2 3 1 0
     * A 3x3 example puzzle that has a solution: 2 3 1 0 5 7 6 4 8
    **********************************************************************/
    public static void main(String[] args) {
        In in = new In();
        System.out.println("Enter board size:");
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        System.out.println("Enter board tiles (from 0 to board size^2 - 1):");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (WorldState ws : solver.solution()) {
            StdOut.println(ws);
        }
    }
}

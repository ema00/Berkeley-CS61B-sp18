package byog.Core;

import java.util.List;
import byog.TileEngine.TETile;



/**
 * Player
 * Represents a player in the world of the game.
 * @author Emanuel Aguirre
 */
public class Player {

    /* Position of the Player in the world. */
    private Point position;
    /* Type of TETile for the player. */
    private final TETile pt;
    /* World in which the player can move around. */
    private final TETile[][] world;
    /* The set of coordinates on which the player can be positioned at any given time. */
    private final List<Point> allowedCoordinates;


    /**
     * Creates the Player object in the world, at the given position. Does not validate if the
     * player has been placed on a valid position (that is, in any of the points in
     * allowedCoordinatesP).
     * @param initialPosition is the initial position of the player in the world.
     * @param allowedCoordinatesP is the set of coordinates on which the player can move.
     * @param playerTile is the type of tile used for drawing the player.
     * @param worldP is the world on which the player is placed (the game rendereded)
     */
    public Player(Point initialPosition, List<Point> allowedCoordinatesP,
                  TETile playerTile, TETile[][] worldP) {
        this.allowedCoordinates = allowedCoordinatesP;
        this.position = initialPosition;
        this.pt = playerTile;
        this.world = worldP;
    }

    /**
     * Returns the current position of the player in the world.
     */
    public Point position() {
        return position;
    }

    /**
     * Moves the player up one tile in the world, if the movement is allowed.
     * @return true if the player has moved, or false if couldn't move.
     */
    public boolean moveUp() {
        if (canMove(0, 1)) {
            move(0, 1);
            return true;
        }
        return false;
    }

    /**
     * Moves the player down one tile in the world, if the movement is allowed.
     * @return true if the player has moved, or false if couldn't move.
     */
    public boolean moveDown() {
        if (canMove(0, -1)) {
            move(0, -1);
            return true;
        }
        return false;
    }

    /**
     * Moves the player right one tile in the world, if the movement is allowed.
     * @return true if the player has moved, or false if couldn't move.
     */
    public boolean moveRight() {
        if (canMove(1, 0)) {
            move(1, 0);
            return true;
        }
        return false;
    }

    /**
     * Moves the player left one tile in the world, if the movement is allowed.
     * @return true if the player has moved, or false if couldn't move.
     */
    public boolean moveLeft() {
        if (canMove(-1, 0)) {
            move(-1, 0);
            return true;
        }
        return false;
    }

    /**
     * Returns true or false whether ot not the player can move from the current position to the
     * current position plus the increments in x and y, dx, and dy.
     * @param dx is the number of tiles to move in the x direction, positive or negative.
     * @param dy is the number of tiles to move in the y direction, positive or negative.
     * @return true if the move is valid, that it, is inside the bounds and if the coordinate
     * of destination is within the coordinates on which the player can be positioned.
     */
    private boolean canMove(int dx, int dy) {
        if (position.x + dx >= world.length - 1 || position.y + dy >= world[0].length - 1) {
            return false;
        } else if (position.x + dx <= 0 || position.y + dy <= 0) {
            return false;
        }

        Point destination = new Point(position.x + dx, position.y + dy);
        return allowedCoordinates.contains(destination);
    }

    /**
     * Moves the position of the Player a number of points (tiles) on x and y.
     * Precondition: the resulting position must be within the bounds of the world.
     * @param dx is the number of tiles to move in the x direction, positive or negative.
     * @param dy is the number of tiles to move in the y direction, positive or negative.
     */
    private void move(int dx, int dy) {
        position = new Point(position.x + dx, position.y + dy);
    }

    /**
     * Draws the player in the world.
     */
    public void draw() {
        world[position.x][position.y] = pt;
    }

}

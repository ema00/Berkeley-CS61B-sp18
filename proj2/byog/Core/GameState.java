package byog.Core;

import java.util.List;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import byog.TileEngine.TETile;



/**
 * GameState
 * This class is a helper to save and load, to disk, the state of a game.
 * Usage, SAVING a state:
 *      gameState.setStateForSaving(allowedCoordinates, walls.getPoints(), player.position());
 *      GameState.save(gameState, STATE_FILENAME);
 * Usage, LOADING a state:
 *      gameState = GameState.load(STATE_FILENAME);
 *      gameState.setWorld(world);
 *      gameState.setPlayerTile(PLAYER_TILE);
 *      allowedCoordinates = gameState.getAllowedPoints();
 *      player = gameState.getPlayer();
 * @author Emanuel Aguirre
 */
public class GameState implements Serializable {

    private static final long serialVersionUID = 123123123123124L;

    /* Variables that are serialized. */
    /* The set of points which the player can traverse (the floor: rooms and hallways). */
    private List<Point> allowedPoints;
    /* The set of points that comprise the walls of the world. */
    private List<Point> wallsPoints;
    /* Position of the player in the world. */
    private Point playerPosition;

    /* Variables that are not serialized. */
    /* Variables related to world and player. */
    private TETile[][] world;
    private TETile playerTile;


    public GameState(TETile[][] worldP, TETile playerTileP) {
        this.world = worldP;
        this.playerTile = playerTileP;
    }

    public List<Point> getAllowedPoints() {
        return allowedPoints;
    }

    private void setAllowedPoints(List<Point> allowedPointsP) {
        this.allowedPoints = allowedPointsP;
    }

    public List<Point> getWallsPoints() {
        return wallsPoints;
    }

    private void setWallsPoints(List<Point> wallsP) {
        this.wallsPoints = wallsP;
    }

    public Point getPlayerPosition() {
        return playerPosition;
    }

    private void setPlayerPosition(Point playerPositionP) {
        this.playerPosition = playerPositionP;
    }

    public void setWorld(TETile[][] worldP) {
        this.world = worldP;
    }

    public void setPlayerTile(TETile playerTileP) {
        this.playerTile = playerTileP;
    }

    /**
     * Returns the player that moves around the world, with its characteristics.
     * Preconditions:
     *  - The game state has to be loaded already.
     * @return a Player instance.
     */
    public Player getPlayer() {
        return new Player(playerPosition, allowedPoints, playerTile, world);
    }

    /**
     * Reads the file that contains the game state, and returns a GameState instance.
     * Preconditions:
     *  - The contents of the file must be valid to generate a GameState instance.
     * @param filename the name of the file that contains the state of a saved game.
     * @return a GameState instance if the file is valid, or else null.
     */
    public static GameState load(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                GameState loadedGameState = (GameState) os.readObject();
                os.close();
                return loadedGameState;
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + filename);
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found.");
                System.exit(0);
            }
        }
        /* In the case no GameState has been saved yet, return null. */
        return null;
    }

    /**
     * Sets the Points for the world, the walls and the position of the player to save the state
     * of the game.
     * @param allowedPts are the Point coordinates that the player is allowed to traverse.
     * @param wallsPts are the Point coordinates that correspond to the walls.
     * @param playerPos is the current position of the player.
     */
    public void setState(List<Point> allowedPts, List<Point> wallsPts, Point playerPos) {
        if (allowedPts == null || wallsPts == null || playerPos == null) {
            throw new IllegalArgumentException("Points for saving game state can not be null.");
        }
        setAllowedPoints(allowedPts);
        setWallsPoints(wallsPts);
        setPlayerPosition(playerPos);
    }

    /**
     * Saves the game state to a file on disk.
     * @param gameState the instance of GameState to be saved to disk.
     * @param filename the name of the file that will contain the state of a saved game.
     */
    public static void save(GameState gameState, String filename) {
        gameState.world = null;
        gameState.playerTile = null;
        File f = new File(filename);
        try {
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(gameState);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

}

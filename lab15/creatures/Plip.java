package creatures;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;



/**
 * CS61B Lab 15: https://sp18.datastructur.es/materials/lab/lab15/lab15
 * An implementation of a motile pacifist photosynthesizer.
 * @author Josh Hug
 * @author Emanuel Aguirre (methods: move, stay, color, replicate, green)
 */
public class Plip extends Creature {

    /** Minimum energy that a Plip can have at any given time. */
    private static final float MIN_ENERGY = 0;
    /** Maximum energy that a Plip can have at any given time. */
    private static final float MAX_ENERGY = 2;
    /** Energy lost by a Plip on a move action. */
    private static final float MOVE_ENERGY_DIFF = -0.15f;
    /** Energy gained by a Plip on a stay action. */
    private static final float STAY_ENERGY_DIFF = 0.2f;
    /** Minimum energy that a Plip must have in order to be able to replicate. */
    private static final float REPLICATION_MIN_ENERGY_THRESHOLD = 1.0f;
    /** Value of red in RGB color for all Plips. */
    private static final int RED = 99;
    /** Value of blue in RGB color for all Plips. */
    private static final int BLUE = 76;
    /** Minimum value of green in RGB color for all Plips. */
    private static final int GREEN_MIN = 63;
    /** Maximum value of green in RGB color for all Plips. */
    private static final int GREEN_MAX = 255;

    /** red color. */
    private final int r;
    /** green color. */
    private int g;
    /** blue color. */
    private final int b;


    /** Creates plip with energy equal to E. */
    public Plip(double e) {
        super("plip");
        r = RED;
        g = green(e);
        b = BLUE;
        energy = e;
    }

    /** Creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }


    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        g = green(energy);
        return color(r, g, b);
    }

    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy = Math.max(MIN_ENERGY, energy + MOVE_ENERGY_DIFF);
    }

    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy = Math.min(MAX_ENERGY, energy + STAY_ENERGY_DIFF);
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Plip replicate() {
        double energy = this.energy / 2;
        this.energy = energy;
        return new Plip(energy);
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Action action = null;
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> cloruses = getNeighborsOfType(neighbors, "clorus");
        if (empties.size() == 0) {
            action = new Action(Action.ActionType.STAY);
        } else {
            if (energy > REPLICATION_MIN_ENERGY_THRESHOLD) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                action = new Action(Action.ActionType.REPLICATE, moveDir);
            } else if (cloruses.size() > 0 && cloruses.size() < 4) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                action = HugLifeUtils.random() < 0.5
                        ? new Action(Action.ActionType.MOVE, moveDir)
                        : new Action(Action.ActionType.STAY);
            } else {
                action = new Action(Action.ActionType.STAY);
            }
        }
        return action;
    }

    /**
     * Returns the value of green color in RGB that corresponds to a Plip with the given energy.
     */
    private int green(double e) {
        return (int) (GREEN_MIN + (e / MAX_ENERGY) * (GREEN_MAX - GREEN_MIN));
    }

}

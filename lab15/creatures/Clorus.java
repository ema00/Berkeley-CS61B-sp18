package creatures;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;
import huglife.HugLifeUtils;



/**
 * CS61B Lab 15: https://sp18.datastructur.es/materials/lab/lab15/lab15
 * An implementation of a creature that moves around and feeds on Plips.
 * @author Emanuel Aguirre
 */
public class Clorus extends Creature {

    /** Energy lost by a Clorus on a move action. */
    private static final float MOVE_ENERGY_DIFF = -0.03f;
    /** Energy gained by a Clorus on a stay action. */
    private static final float STAY_ENERGY_DIFF = -0.01f;
    /** Minimum energy that a Clorus must have in order to be able to replicate. */
    private static final float REPLICATION_MIN_ENERGY_THRESHOLD = 1.0f;
    /** Value of red in RGB color for all Cloruses. */
    private static final int RED = 34;
    /** Value of blue in RGB color for all Cloruses. */
    private static final int GREEN = 0;
    /** Value of blue in RGB color for all Cloruses. */
    private static final int BLUE = 231;


    /** Creates clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    /** Creates a clorus with energy equal to 1. */
    public Clorus() {
        this(1);
    }


    /** Should return a color with red = 34, blue = 231, and green = 0. */
    public Color color() {
        return color(RED, GREEN, BLUE);
    }

    /**
     * When attacks another creature, it gains the other creature's energy.
     */
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    /** When Cloruses moves should lose 0.03 energy. */
    @Override
    public void move() {
        energy += MOVE_ENERGY_DIFF;
    }

    /** When Cloruses stay should lose 0.01 energy. */
    @Override
    public void stay() {
        energy += STAY_ENERGY_DIFF;
    }

    /** Cloruses and their offspring each get 50% of the energy, with none
     * lost to the process. Returns a baby Clorus.
     */
    @Override
    public Clorus replicate() {
        double energy = this.energy / 2;
        this.energy = energy;
        return new Clorus(energy);
    }

    /** Cloruses take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if any Plips, ATTACK one of them randomly.
     *  3. Otherwise, if energy >= 1, REPLICATE to any empty adjacent space.
     *  4. Otherwise, MOVE to a random empty adjacent space.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Action action = null;
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.size() == 0) {
            action = new Action(Action.ActionType.STAY);
        } else {
            if (plips.size() > 0) {
                Direction moveDir = HugLifeUtils.randomEntry(plips);
                action = new Action(Action.ActionType.ATTACK, moveDir);
            } else if (energy > REPLICATION_MIN_ENERGY_THRESHOLD) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                action = new Action(Action.ActionType.REPLICATE, moveDir);
            } else {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                action = new Action(Action.ActionType.MOVE, moveDir);
            }
        }
        return action;
    }

}

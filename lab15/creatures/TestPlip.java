package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;



/** Tests the Plip class
 *  @author Emanuel Aguirre
 */
public class TestPlip {

    /* Replace with the magic word given in lab.
     * If you are submitting early, just put in "early" */
    public static final String MAGIC_WORD = "";

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        double delta = 0.0000000001;
        double energy = 1;
        Plip parent = new Plip(energy);
        Plip child = parent.replicate();
        assertNotSame(parent, child);
        assertEquals(parent.energy(), energy / 2, delta);
        assertEquals(parent.energy(), child.energy(), delta);
    }

    @Test
    public void testChooseActionStay1() {
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionStay2() {
        Plip p = new Plip(0.8);
        HashMap<Direction, Occupant> empties = new HashMap<Direction, Occupant>();
        empties.put(Direction.TOP, new Empty());
        empties.put(Direction.BOTTOM, new Empty());
        empties.put(Direction.LEFT, new Empty());
        empties.put(Direction.RIGHT, new Empty());

        Action actual = p.chooseAction(empties);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionReplicate() {
        Plip p = new Plip(1.5);
        HashMap<Direction, Occupant> emptyTop = new HashMap<Direction, Occupant>();
        emptyTop.put(Direction.TOP, new Empty());
        emptyTop.put(Direction.BOTTOM, new Impassible());
        emptyTop.put(Direction.LEFT, new Impassible());
        emptyTop.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(emptyTop);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }

} 

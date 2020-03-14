package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;


/** Tests the Clorus class
 *  @author Emanuel Aguirre
 */
public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        double delta = 0.0000000001;
        double energy = 1;
        Clorus parent = new Clorus(energy);
        Clorus child = parent.replicate();
        assertNotSame(parent, child);
        assertEquals(parent.energy(), energy / 2, delta);
        assertEquals(parent.energy(), child.energy(), delta);
    }

    @Test
    public void testChooseActionStay() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Plip());
        surrounded.put(Direction.RIGHT, new Plip());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionAttack1() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> plipTop = new HashMap<Direction, Occupant>();
        plipTop.put(Direction.TOP, new Plip());
        plipTop.put(Direction.BOTTOM, new Empty());
        plipTop.put(Direction.LEFT, new Impassible());
        plipTop.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(plipTop);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.TOP);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionAttack2() {
        Clorus c = new Clorus(0.4);
        HashMap<Direction, Occupant> plipBottom = new HashMap<Direction, Occupant>();
        plipBottom.put(Direction.BOTTOM, new Plip());
        plipBottom.put(Direction.TOP, new Empty());
        plipBottom.put(Direction.LEFT, new Impassible());
        plipBottom.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(plipBottom);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionReplicate() {
        Clorus c = new Clorus(1.5);
        HashMap<Direction, Occupant> emptyTop = new HashMap<Direction, Occupant>();
        emptyTop.put(Direction.TOP, new Empty());
        emptyTop.put(Direction.BOTTOM, new Impassible());
        emptyTop.put(Direction.LEFT, new Impassible());
        emptyTop.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(emptyTop);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionMove() {
        Clorus c = new Clorus(0.5);
        HashMap<Direction, Occupant> emptyTop = new HashMap<Direction, Occupant>();
        emptyTop.put(Direction.TOP, new Empty());
        emptyTop.put(Direction.BOTTOM, new Impassible());
        emptyTop.put(Direction.LEFT, new Impassible());
        emptyTop.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(emptyTop);
        Action expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }

} 

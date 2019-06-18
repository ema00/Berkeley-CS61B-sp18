import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;


/**
 * TestArrayDequeGold
 */
public class TestArrayDequeGold {

    @Test
    public void randomTestArrayDeque() {
        final int NUM_TESTS = 50;

        /* Implementation under test */
        StudentArrayDeque<Integer> dequeTest = new StudentArrayDeque<>();
        /* Reference implementation */
        ArrayDequeSolution<Integer> dequeRef = new ArrayDequeSolution<>();

        /* add and remove, starting empty, to and from both implementations */
        for (int i = 0; i < NUM_TESTS; i++) {
            Integer randInt = StdRandom.uniform(0, 5);
            switch (randInt) {
                case 0:
                    dequeTest.addFirst(randInt);
                    dequeRef.addFirst(randInt);
                    break;
                case 1:
                    dequeTest.addLast(randInt);
                    dequeRef.addLast(randInt);
                    break;
                case 2:
                    if (!dequeTest.isEmpty() && !dequeRef.isEmpty()) {
                        assertEquals(dequeRef.removeFirst(), dequeTest.removeFirst());
                    }
                    break;
                case 3:
                    if (!dequeTest.isEmpty() && !dequeRef.isEmpty()) {
                        assertEquals(dequeRef.removeLast(), dequeTest.removeLast());
                    }
                    break;
                case 4:
                    assertEquals(dequeRef.size(), dequeTest.size());
                    break;
            }
        }

        /* add numbers to reference implementation and implementation under test */
        for (int i = 0; i < NUM_TESTS; i++) {
            Integer randInt = StdRandom.uniform(-10000, 10000);

            if (i % 2 == 0) {
                dequeTest.addFirst(randInt);
                dequeRef.addFirst(randInt);
            }
            else {
                dequeTest.addLast(randInt);
                dequeRef.addLast(randInt);
            }
        }
        /* test the student's implementation against the reference implementation */
        while (!dequeRef.isEmpty()) {
            double randDouble = StdRandom.uniform();

            assertEquals(dequeRef.isEmpty(), dequeTest.isEmpty());

            if (randDouble < 0.5) {
                assertEquals(dequeRef.removeFirst(), dequeTest.removeFirst());
            } else {
                assertEquals(dequeRef.removeLast(), dequeTest.removeLast());
            }
        }
    }

}

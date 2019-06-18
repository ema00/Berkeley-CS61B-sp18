import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;


/**
 * TestArrayDequeGold
 */
public class TestArrayDequeGold {

    @Test
    public void randomTestArrayDeque() {

        /* Number of elements to load to the Deque under test and the reference implementation */
        final int NUM_TESTS = 8;

        /* Implementation under test */
        StudentArrayDeque<Integer> dequeTest = new StudentArrayDeque<>();
        /* Reference implementation */
        ArrayDequeSolution<Integer> dequeRef = new ArrayDequeSolution<>();

        /* Message to be displayed to the user, containing the sequence of method calls */
        String message = "";
        String methodCallString = "";

        /* add numbers to reference implementation and implementation under test */
        for (int i = 0; i < NUM_TESTS; i++) {
            Integer randInt = StdRandom.uniform(-10000, 10000);

            if (i % 2 == 0) {
                methodCallString = "addFirst(" + randInt + ")" + "\n";
                message += methodCallString;

                dequeTest.addFirst(randInt);
                dequeRef.addFirst(randInt);
            }
            else {
                methodCallString = "addLast(" + randInt + ")" + "\n";
                message += methodCallString;

                dequeTest.addLast(randInt);
                dequeRef.addLast(randInt);
            }
        }
        /* test the student's implementation against the reference implementation */
        while (!dequeRef.isEmpty()) {
            double randDouble = StdRandom.uniform();

            if (randDouble < 0.5) {
                methodCallString = "removeFirst()" + "\n";
                message += methodCallString;

                assertEquals(message, dequeRef.removeFirst(), dequeTest.removeFirst());
            } else {
                methodCallString = "removeLast()" + "\n";
                message += methodCallString;

                assertEquals(message, dequeRef.removeLast(), dequeTest.removeLast());
            }
        }
    }

}

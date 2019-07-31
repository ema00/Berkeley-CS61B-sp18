package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug / Emanuel Aguirre
 */

public class TestArrayRingBuffer {

    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }

    @Test
    public void testCapacity() {
        int CAPACITY_1 = 2;
        int CAPACITY_2 = 300;
        ArrayRingBuffer<Long> arb1 = new ArrayRingBuffer<>(CAPACITY_1);
        ArrayRingBuffer<Long> arb2 = new ArrayRingBuffer<>(CAPACITY_2);

        arb2.enqueue(1L);
        arb2.enqueue(2L);
        arb2.dequeue();

        assertEquals(CAPACITY_1, arb1.capacity());
        assertEquals(CAPACITY_2, arb2.capacity());
    }

    // TODO
    @Test
    public void testFillCount() {
        int CAPACITY = 300;
        int NUM_ELEMENTS = 28;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);

        assertEquals(0L, arb.fillCount());

        for (Long i = 1L; i <= NUM_ELEMENTS; i++) {
            arb.enqueue(i);
        }

        assertEquals(NUM_ELEMENTS, arb.fillCount());
    }

    @Test
    public void testEmpty() {
        int CAPACITY = 2;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);
        arb.enqueue(1L);
        arb.enqueue(2L);

        assertFalse(arb.isEmpty());

        arb.dequeue();
        assertFalse(arb.isEmpty());

        arb.dequeue();
        assertTrue(arb.isEmpty());
    }

    @Test
    public void testFull() {
        int CAPACITY = 2;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);
        arb.enqueue(1L);
        arb.enqueue(2L);

        assertTrue(arb.isFull());

        arb.dequeue();
        assertFalse(arb.isFull());

        arb.dequeue();
        assertFalse(arb.isFull());
    }

    @Test
    public void testPeek() {
        int CAPACITY = 2;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);
        arb.enqueue(1L);
        arb.enqueue(2L);

        assertEquals(1L, (long) arb.peek());

        arb.dequeue();
        assertEquals(2L, (long) arb.peek());

        arb.dequeue();
        assertNull(arb.peek());
    }

    @Test
    public void testEnqueueDeque01() {
        int CAPACITY = 2;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);
        arb.enqueue(1L);
        arb.enqueue(2L);

        assertEquals(1L, (long) arb.dequeue());
        assertEquals(2L, (long) arb.dequeue());
    }

    @Test
    public void testEnqueueDeque02() {
        int CAPACITY = 4;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);

        // enqueue 1, 2, 3, 4
        arb.enqueue(1L);
        arb.enqueue(2L);
        arb.enqueue(3L);
        arb.enqueue(4L);

        // dequeue 1, 2
        arb.dequeue();
        arb.dequeue();

        // enqueue 5, 6
        arb.enqueue(5L);
        arb.enqueue(6L);

        assertEquals(3L, (long) arb.dequeue());
        assertEquals(4L, (long) arb.dequeue());
        assertEquals(5L, (long) arb.dequeue());
        assertEquals(6L, (long) arb.dequeue());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 

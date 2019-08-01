package synthesizer;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug / Emanuel Aguirre
 */

public class TestArrayRingBuffer {

    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }

    @Test(expected = RuntimeException.class)
    public void testConstructorRuntimeException() {
        int CAPACITY = 0;
        BoundedQueue<Long> bq = new ArrayRingBuffer<>(CAPACITY);
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
    }

    @Test(expected = RuntimeException.class)
    public void testPeekRuntimeException() {
        int CAPACITY = 1;
        BoundedQueue<Long> bq = new ArrayRingBuffer<>(CAPACITY);

        bq.dequeue();
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

    @Test(expected = RuntimeException.class)
    public void testEnqueueRuntimeException() {
        int CAPACITY = 1;
        BoundedQueue<Long> bq = new ArrayRingBuffer<>(CAPACITY);

        bq.enqueue(1L);
        bq.enqueue(1L);
    }

    @Test(expected = RuntimeException.class)
    public void testDequeueRuntimeException() {
        int CAPACITY = 1;
        BoundedQueue<Long> bq = new ArrayRingBuffer<>(CAPACITY);

        bq.enqueue(1L);
        bq.dequeue();
        bq.dequeue();
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

    @Test
    public void testIterator1() {
        int CAPACITY = 4;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);

        // enqueue 1, 2, 3, 4
        for (Long i = 1L; i <= 4L; i++) {
            arb.enqueue(i);
        }

        Iterator<Long> it = arb.iterator();

        for (Long i = 1L; i <= 4L; i++) {
            assertTrue(it.hasNext());
            assertEquals(i, it.next());
        }
        assertFalse(it.hasNext());
    }

    @Test(expected = RuntimeException.class)
    public void testIteratorRuntimeException() {
        int CAPACITY = 1;
        ArrayRingBuffer<Long> arb = new ArrayRingBuffer<>(CAPACITY);

        Iterator<Long> it = arb.iterator();
        it.next();
    }


    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }

} 

package synthesizer;

import java.util.Iterator;


/**
 * ArrayRingBuffer
 * Ring Buffer implemented using an Array as its underlying data structure.
 * Has a fixed capacity.
 * @author Emanuel Aguirre
 */
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>  {

    /* Index for the next dequeue or peek. The index of the least recently inserted item. */
    private int first;
    /* Index for the next enqueue. One beyond de most recently inserted item. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.rb = (T[]) new Object[capacity];
        this.capacity = rb.length;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
    }

    /**
     * Adds item x to the end of the ring buffer.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow.");
        }
        rb[last] = x;
        last = (last + 1) % capacity;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow.");
        }
        T item = rb[first];
        rb[first] = null;
        first = (first + 1) % capacity;
        fillCount -= 1;
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        return this.isEmpty() ? null : rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}

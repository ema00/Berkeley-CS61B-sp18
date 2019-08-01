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
    private final T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        if (capacity < 1) {
            throw new RuntimeException("Attempt to initialize Array Ring Buffer to size < 1.");
        }
        this.rb = (T[]) new Object[capacity];
        this.capacity = rb.length;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
    }

    /**
     * Adds item x to the end of the Ring Buffer.
     */
    @Override
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring Buffer overflow.");
        }
        rb[last] = x;
        last = (last + 1) % capacity;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the Ring Buffer.
     */
    @Override
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow.");
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
    @Override
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("Attempt to peek from empty Ring Buffer.");
        }
        return rb[first];
    }

    /**
     * @return an Iterator over the elements of the Ring Buffer.
     */
    @Override
    public Iterator<T> iterator() {
        return new IteratorAB<T>(this.rb);
    }


    /**
     * Iterator class. Represents an Iterator over the elements of the Ring Buffer.
     */
    private class IteratorAB<T> implements Iterator<T> {

        /* Items on which to iterate. */
        private final T[] items;
        /* Current position of the Iterator. */
        private int index;

        private IteratorAB(T[] items) {
            this.items = items;
            this.index = 0;
        }

        /**
         * @return true or false whether there are more elements to iterate or not.
         */
        @Override
        public boolean hasNext() {
            return index < fillCount();
        }

        /**
         * @return the next item of the iteration.
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new RuntimeException("Attempt to iterate beyond the end of Array Buffer.");
            }
            T item = items[index];
            index += 1;
            return item;
        }

    }

}

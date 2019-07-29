package synthesizer;


/**
 * AbstractBoundedQueue
 * Inherits behavior from BoundedQueue interface.
 * Implements methods that are common to all implementations of BoundedQueue.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    /* Number of items currently in the Queue */
    protected int fillCount;
    /* Capacity of the Queue */
    protected int capacity;

    /* Returns the capacity of the Queue */
    public int capacity() {
        return this.capacity;
    }

    /* Returns the number of items currently in the Queue */
    public int fillCount() {
        return this.fillCount;
    }

}

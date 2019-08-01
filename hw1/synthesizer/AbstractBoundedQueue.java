package synthesizer;


/**
 * AbstractBoundedQueue
 * Inherits behavior from BoundedQueue interface.
 * Implements methods that are common to all implementations of BoundedQueue.
 * @author Emanuel Aguirre
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    /* Number of items currently in the Queue */
    protected int fillCount;
    /* Capacity of the Queue */
    protected int capacity;

    /* Returns the capacity of the Queue */
    @Override
    public int capacity() {
        return this.capacity;
    }

    /* Returns the number of items currently in the Queue */
    @Override
    public int fillCount() {
        return this.fillCount;
    }

}

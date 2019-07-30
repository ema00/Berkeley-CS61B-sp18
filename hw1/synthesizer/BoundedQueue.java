package synthesizer;


/**
 * BoundedQueue
 * Interface for a Queue that has a fixed capacity.
 * Items can only be enqueued at the back of the Queue.
 * Items can only be dequeued at the front of the Queue.
 * No item is allowed to be enqueued if the Queue is full.
 * @author CS 61B / Emanuel Aguirre
 */
public interface BoundedQueue<T> {

    /* Return the size of the Queue */
    int capacity();

    /* Return the number of items currently in the Queue */
    int fillCount();

    /* Add item x to the back of the Queue */
    void enqueue(T x);

    /* Remove and return the front item from the Queue */
    T dequeue();

    /* Return (without removing) the front item of the Queue */
    T peek();

    /* Return whether the Queue is empty or not */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /* Return whether the Queue is full or not */
    default boolean isFull() {
        return fillCount() == capacity();
    }

}

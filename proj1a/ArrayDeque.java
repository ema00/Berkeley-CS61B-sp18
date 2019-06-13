/**
 * ArrayDeque
 * Double Ended Queue (deque) data structure.
 * Implemented internally using an array.
 * @param <T> is the type of objects to be stored in the deque.
 */

public class ArrayDeque<T> {

    private static final int INITIAL_LENGTH = 4;
    private static final int UPSIZE_FACTOR = 2;
    private static final int DOWNSIZE_FACTOR = 4;


    private T arr[];
    private int start;
    private int end;
    private int size;


    public ArrayDeque() {
        arr = (T[]) new Object[INITIAL_LENGTH];
        start = 0;
        end = arr.length - 1;
        size = 0;
    }

    /**
     * Adds an item at the beginning of the deque.
     * @param item is the item to be added at the beginning of the deque.
     */
    public void addFirst(T item) {
        if (size == arr.length) {
            changeCapacity(arr.length * UPSIZE_FACTOR);
        }
        arr[--start] = item;
        size++;
    }

    /**
     * Adds an item at the end of the deque.
     * @param item is the item to be added at the end of the deque.
     */
    public void addLast(T item) {
        if (size == arr.length) {
            changeCapacity(arr.length * UPSIZE_FACTOR);
        }
        arr[++start] = item;
        size++;
    }

    /**
     * Returns true or false whether the list is empty or not.
     * @return true if the list is empty and false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    // TODO
    public int size() {

    }

    // TODO
    public void printDeque() {

    }

    // TODO
    public T removeFirst() {

    }

    // TODO
    public T removeLast() {

    }

    // TODO
    public T get(int index) {

    }

    /**
     * Changes the size of the underlying array to the length passed as parameter.
     * @param length is the new length of th underlying array.
     */
    private void changeCapacity(int length) {
        T[] resized = (T[]) new Object[length];
        for(i = 0; i < size; i++) {
            int src = (start + i) % arr.length;
            int dest = i;
            resized[dest] = arr[src];
        }
        arr = resized;
        start = 0;
        end = size - 1;
    }

}

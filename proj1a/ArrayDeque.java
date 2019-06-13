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
        start = (--start + arr.length) % arr.length;
        arr[start] = item;
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
        end = (++end) % arr.length;
        arr[end] = item;
        size++;
    }

    /**
     * Returns true or false whether the list is empty or not.
     * @return true if the list is empty and false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the deque.
     * @return the size of the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last.
     */
    public void printDeque() {
        for(int i = 0; i < size; i++) {
            int index = (start + i) % arr.length;
            System.out.print(arr[index] + " ");
        }
    }

    /**
     * Removes the first item in the deque and returns it.
     * @return the first item in the deque.
     */
    public T removeFirst() {
        if (size <= 0) {
            return null;
        }
        if (size < arr.length / DOWNSIZE_FACTOR && size > INITIAL_LENGTH) {
            changeCapacity(arr.length / UPSIZE_FACTOR);
        }
        T item = arr[start];
        start = (start + 1) % arr.length;
        size--;
        return item;
    }

    /**
     * Removes the last item in the deque and returns it.
     * @return the last item in the deque.
     */
    public T removeLast() {
        if (size <= 0) {
            return null;
        }
        if (size < arr.length / DOWNSIZE_FACTOR && size > INITIAL_LENGTH) {
            changeCapacity(arr.length / UPSIZE_FACTOR);
        }
        T item = arr[end];
        end = ((end - 1) + arr.length) % arr.length;
        size--;
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front item.
     * @param index of the item to be returned, 0 based.
     * @return the item at the given index, or null if the index is beyond the
     * length of the deque.
     */
    public T get(int index) {
        return arr[(start + index) % arr.length];
    }

    /**
     * Changes the size of the underlying array to the length passed as parameter.
     * @param length is the new length of th underlying array.
     */
    private void changeCapacity(int length) {
        T[] resized = (T[]) new Object[length];
        for(int i = 0; i < size; i++) {
            int src = (start + i) % arr.length;
            int dest = i;
            resized[dest] = arr[src];
        }
        arr = resized;
        start = 0;
        end = size - 1;
    }

}

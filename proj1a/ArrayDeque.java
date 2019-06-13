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

    // TODO
    public void addFirst(T item) {

    }

    // TODO
    public void addLast(T item) {

    }

    // TODO
    public boolean isEmpty() {

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

    // TODO
    private void increaseCapacity() {

    }

}

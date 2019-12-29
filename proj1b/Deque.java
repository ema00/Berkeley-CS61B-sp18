


/**
 * Deque
 * Interface of a Double Ended Queue.
 * @author Emanuel Aguirre
 */
public interface Deque<T> {

    void addFirst(T item);

    void addLast(T item);

    boolean isEmpty();

    int size();

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int index);

}

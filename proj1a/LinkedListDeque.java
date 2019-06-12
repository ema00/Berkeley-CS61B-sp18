/**
 * LinkedListDeque
 * Double Ended Queue (deque) data structure.
 * Implemented internally using a doubly linked list.
 * @param <T> is the type of objects to be stored in the deque.
 */

public class LinkedListDeque<T> {

    /**
     * A node for the internal representation based on a doubly linked list.
     * @param <T> is the type of objects to be stored in the deque.
     */
    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;
    }


    private Node<T> sentinel;
    private int size;


    public LinkedListDeque() {

    }


    public void addFirst(T item) {

    }

    public void addLast(T item) {

    }

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return 0;
    }

    public void printDeque() {
        System.out.println("hello!!!");
    }

    public T removeFirst() {
        return new Node<T>().value;
    }

    public T removeLast() {
        return new Node<T>().value;
    }

    public T get(int index) {
        return new Node<T>().value;
    }

    public T getRecursive(int index) {
        return new Node<T>().value;
    }

}

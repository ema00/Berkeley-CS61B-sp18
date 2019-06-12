/**
 * LinkedListDeque
 * Double Ended Queue (deque) data structure.
 * Implemented internally using a doubly linked list.
 * The doubly linked list is a circular list with a single sentinel node. The
 * sentinel node is used to simplify the insertion and removal operations.
 * @param <T> is the type of objects to be stored in the deque.
 */

public class LinkedListDeque<T> {

    /**
     * Node
     * A node for the internal representation based on a doubly linked list.
     * Every node has a pointer to the next and previous nodes in the linked list.
     * @param <T> is the type of objects to be stored in the deque.
     */
    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;


        Node() { }

        Node(T value) {
            this.value = value;
        }
    }


    /* LinkedListDeque class implementation */

    private Node<T> sentinel;
    private int size;


    public LinkedListDeque() {
        sentinel = new Node<T>();
    }

    public LinkedListDeque(T value) {
        sentinel = new Node<T>();
        sentinel.next = new Node<T>(value);
        sentinel.prev = sentinel.next;
    }


    // TODO
    public void addFirst(T item) {

    }

    // TODO
    public void addLast(T item) {

    }

    // TODO
    public boolean isEmpty() {
        return true;
    }

    // TODO
    public int size() {
        return 0;
    }

    // TODO
    public void printDeque() {
        System.out.println("hello!!!");
    }

    // TODO
    public T removeFirst() {
        return new Node<T>().value;
    }

    // TODO
    public T removeLast() {
        return new Node<T>().value;
    }

    // TODO
    public T get(int index) {
        return new Node<T>().value;
    }

    // TODO
    public T getRecursive(int index) {
        return new Node<T>().value;
    }

}

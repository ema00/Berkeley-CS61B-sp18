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
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(T value) {
        sentinel = new Node<T>();
        sentinel.next = new Node<T>(value);
        sentinel.prev = sentinel.next;
        size = 1;
    }


    // TODO
    public void addFirst(T value) {

    }

    // TODO
    public void addLast(T value) {

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

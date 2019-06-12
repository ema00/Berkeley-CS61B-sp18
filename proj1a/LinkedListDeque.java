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
        super();
        this.addFirst(value);
    }


    /**
     * Adds an item at the beginning of the deque.
     * @param value the item to be added at the beginning of the deque.
     */
    public void addFirst(T value) {
        Node<T> node = new Node<T>(value);
        Node<T> next = sentinel.next;

        node.next = next;
        next.prev = node;
        sentinel.next = node;
        node.prev = sentinel;

        size += 1;
    }

    /**
     * Adds an item at the end of the deque.
     * @param value the item to be added at the end of the deque.
     */
    public void addLast(T value) {
        Node<T> node = new Node<T>(value);
        Node<T> prev = sentinel.prev;

        node.prev = prev;
        prev.next = node;
        sentinel.prev = node;
        node.next = sentinel;

        size += 1;
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
        for (Node<T> p = sentinel.next; p != sentinel; p = p.next) {
            System.out.print(p.value + " ");
        }
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

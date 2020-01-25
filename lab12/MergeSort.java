import edu.princeton.cs.algs4.Queue;



/**
 * MergeSort
 * CS61B, Lab 12: https://sp18.datastructur.es/materials/lab/lab12/lab12
 *
 * Implementation of the MergeSort algorithm on a generic queue.
 *
 * @author CS61B staff (interface)
 * @author Emanuel Aguirre (implementation)
 */
public class MergeSort {

    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items
    ) {
        Queue<Queue<Item>> queue = new Queue<>();
        // This is to avoid exceptions when trying to sort an empty queue.
        if (queue.isEmpty()) {
            queue.enqueue(new Queue<>());
        }
        for (Item item : items) {
            Queue<Item> singleItemQueue = new Queue<>();
            singleItemQueue.enqueue(item);
            queue.enqueue(singleItemQueue);
        }
        return queue;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2
    ) {
        Queue<Item> result = new Queue<>();
        while (!(q1.isEmpty() && q2.isEmpty())) {
            if (q1.isEmpty()) {
                result.enqueue(q2.dequeue());
            } else if (q2.isEmpty()) {
                result.enqueue(q1.dequeue());
            } else {
                result.enqueue(getMin(q1, q2));
            }
        }
        return result;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items
    ) {
        Queue<Queue<Item>> siq = makeSingleItemQueues(items);
        Queue<Queue<Item>> miq = mergeSortMultipleItemQueues(siq);
        return miq.dequeue();
    }

    /**
     * Recursively sorts a queue of queues of sorted items, using Merge Sort.
     * Works by dequeuing the sorted queues in pairs, merging them, and adding them to a new
     * queue of sorted queues, and then recursively calling this method on that queue.
     * A multiple item queue is considered sorted if its size is 0 or 1. Recursion base case.
     * Precondition: The queues in "miq" must be sorted.
     * Example: mergeSortMultipleItemQueues((8, 9), (1, 3)) returns ((1, 3, 8, 9))
     * @param miq is a queue of queues of sorted items.
     * @param <Item> is the type of item to be sorted; must implement Comparable interface.
     * @return a queue containing queues of sorted items.
     * @author Emanuel Aguirre
     */
    private static <Item extends Comparable> Queue<Queue<Item>> mergeSortMultipleItemQueues(
            Queue<Queue<Item>> miq
    ) {
        if (miq.size() <= 1) {
            return miq;
        }

        Queue<Queue<Item>> msMiq = new Queue<>();
        while (!miq.isEmpty()) {
            Queue<Item> q1 = !miq.isEmpty() ? miq.dequeue() : new Queue<>();
            Queue<Item> q2 = !miq.isEmpty() ? miq.dequeue() : new Queue<>();
            Queue<Item> msq = mergeSortedQueues(q1, q2);
            msMiq.enqueue(msq);
        }
        return mergeSortMultipleItemQueues(msMiq);
    }


    /**
     * Main method used to test the implementation.
     */
    public static void main(String[] args) {
        String a = "Alice";
        String e = "Ethan";
        String m = "Michael";
        String v = "Vanessa";
        String z = "Zoey";

        Queue<String> unsorted = new Queue<>();
        unsorted.enqueue(m);
        unsorted.enqueue(z);
        unsorted.enqueue(a);
        unsorted.enqueue(v);
        unsorted.enqueue(e);

        Queue<String> sorted = MergeSort.mergeSort(unsorted);

        System.out.println("Unsorted queue:");
        System.out.println(unsorted.toString());
        System.out.println("Sorted queue:");
        System.out.println(sorted.toString());
    }

}

import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;


/**
 * QuickSort
 * CS61B, Lab 12: https://sp18.datastructur.es/materials/lab/lab12/lab12
 *
 * Implementation of the QuickSort algorithm on a generic queue.
 *
 * @author CS61B staff (interface)
 * @author Emanuel Aguirre (some implementation)
 */
public class QuickSort {

    /**
     * Returns a new queue that contains the given queues catenated together.
     * The items in q2 will be catenated after all of the items in q1.
     * @author CS61B staff
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /**
     * Returns a random item from the given queue.
     * @author CS61B staff
     */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     * @author CS61B staff (interface)
     * @author Emanuel Aguirre (implementation)
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater
    ) {
        Iterator<Item> iterator = unsorted.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            int cmp = item.compareTo(pivot);
            if (cmp < 0) {
                less.enqueue(item);
            } else if (cmp > 0) {
                greater.enqueue(item);
            } else {
                equal.enqueue(item);
            }
        }
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     * Sorts the Queue recursively using the Quicksort algorithm.
     * @author CS61B staff (interface)
     * @author Emanuel Aguirre (implementation)
     */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items
    ) {
        if (items.size() <= 1) {
            return items;
        }

        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();
        Item item = getRandomItem(items);
        partition(items, item, less, equal, greater);
        less = quickSort(less);
        greater = quickSort(greater);
        return catenate(less, catenate(equal, greater));
    }


    /**
     * Main method used to test the implementation.
     * @author Emanuel Aguirre
     */
    public static void main(String[] args) {
        String a = "Antwan";
        String e = "Edward";
        String m = "Michelle";
        String m2 = "Michelle";
        String v = "Victor";
        String z = "Zachary";

        Queue<String> unsorted = new Queue<>();
        unsorted.enqueue(m);
        unsorted.enqueue(z);
        unsorted.enqueue(a);
        unsorted.enqueue(v);
        unsorted.enqueue(m2);
        unsorted.enqueue(e);

        Queue<String> sorted = QuickSort.quickSort(unsorted);

        System.out.println("Unsorted queue:");
        System.out.println(unsorted.toString());
        System.out.println("Sorted queue:");
        System.out.println(sorted.toString());


        Queue<String> unsortedEmpty = new Queue<>();

        Queue<String> sortedEmpty = QuickSort.quickSort(unsortedEmpty);

        System.out.println("Unsorted empty queue:");
        System.out.println(unsortedEmpty.toString());
        System.out.println("Sorted empty queue:");
        System.out.println(sortedEmpty.toString());
    }

}

/** Performs some basic array-based deque tests. */
public class ArrayDequeTest {

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out get() checks. */
    public static boolean checkGet(Integer expected, Integer actual) {
        if (expected != actual) {
            System.out.println("get() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    /** Adds a few things to the list, checking isEmpty() and size() are correct,
      * finally printing the results.
      *
      * && is the "and" operation. */
    public static void addIsEmptySizeTest() {

        System.out.println("Running add/isEmpty/Size test.");

        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);

    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);

    }

    /** Adds 4 items, then removes 2, checks size and prints so the user can check. */
    public static void removeTest() {

        System.out.println("Running remove test.");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        lld1.addFirst(0);
        lld1.addLast(20);
        lld1.addLast(null);
        lld1.addLast(30);
        lld1.addLast(40);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be size 5
        passed = checkSize(5, lld1.size()) && passed;

        lld1.removeLast();
        lld1.removeLast();
        // should be size 3
        passed = checkSize(3, lld1.size()) && passed;

        printTestStatus(passed);

        System.out.println("Printing out deque (should print: 10 20 null):");
        lld1.printDeque();

    }

    /** Adds 4 items, the gets items at certain positions, then removes 2, and prints. */
    public static void getTest() {

        System.out.println("\n");
        System.out.println("Running get test.");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        lld1.addFirst(10);
        lld1.addFirst(0);
        lld1.addLast(20);
        lld1.addLast(30);
        // should not be empty
        boolean passed = checkEmpty(false, lld1.isEmpty());

        System.out.println("Testing get().");
        // return null if index is beyond the length
        passed = checkGet(null, lld1.get(4)) && passed;
        passed = checkGet(0, lld1.get(0)) && passed;
        passed = checkGet(10, lld1.get(1)) && passed;
        passed = checkGet(30, lld1.get(3)) && passed;

        printTestStatus(passed);

    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        removeTest();
        getTest();
    }
}

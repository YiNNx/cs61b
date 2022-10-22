/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    public static boolean checkValue(int expected, int actual) {
        if (expected != actual) {
            System.out.println("[ERROR] get() returned " + actual + ", but expected: " + expected);
            return false;
        }
        System.out.println("[PASS] get() returned " + actual + ", but expected: " + expected);
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", expected: " + expected);
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

    /**
     * Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     * <p>
     * && is the "and" operation.
     */
    public static void addIsEmptySizeTest() {
        LinkedListDeque lld1 = new LinkedListDeque();

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

    /**
     * Adds an item, then removes an item, and ensures that dll is empty afterwards.
     */
    public static void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(11);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast(12);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addFirst(10);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        int val1 = lld1.get(0);
        passed = checkValue(10, val1);

        int val2 = lld1.getRecursive(1);
        passed = checkValue(11, val2);

        int val3 = lld1.getRecursive(2);
        passed = checkValue(12, val3);

        printTestStatus(passed);
    }


    public static void main(String[] args) {
        System.out.println("Running tests.\n");
//        addIsEmptySizeTest();
        addRemoveTest();
    }
}

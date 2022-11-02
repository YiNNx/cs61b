import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import java.util.Random;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> tas = new Queue<>();
        Random rand = new Random();
        for (int i = 1023; i >=0; i--) {
            int n = rand.nextInt(2048);
            tas.enqueue(n);
        }
        Queue<Integer> res=QuickSort.quickSort(tas);
        assertTrue(isSorted(res));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> tas = new Queue<>();
        Random rand = new Random();
        for (int i = 1023; i >=0; i--) {
            int n = rand.nextInt(2048);
            tas.enqueue(n);
        }
        Queue<Integer> res=MergeSort.mergeSort(tas);
        assertTrue(isSorted(res));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items A Queue of items
     * @return true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}

package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> list_no_resize = new AListNoResizing<>();
        BuggyAList<Integer> buggy_list = new BuggyAList<>();

        // Adding 3 elements to both lists
        list_no_resize.addLast(4);
        buggy_list.addLast(4);
        list_no_resize.addLast(5);
        buggy_list.addLast(5);
        list_no_resize.addLast(6);
        buggy_list.addLast(6);

        // Remove and compare 2 lists when calling removeLast
        int list_no_resize_pop, buggy_list_pop;
        list_no_resize_pop = list_no_resize.getLast();
        buggy_list_pop = buggy_list.getLast();
        assertEquals(list_no_resize_pop, buggy_list_pop);   // 4
        list_no_resize_pop = list_no_resize.getLast();
        buggy_list_pop = buggy_list.getLast();
        assertEquals(list_no_resize_pop, buggy_list_pop);   // 5
        list_no_resize_pop = list_no_resize.getLast();
        buggy_list_pop = buggy_list.getLast();
        assertEquals(list_no_resize_pop, buggy_list_pop);   // 6
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> L_buggy = new BuggyAList<>();

        int N = 20000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                L_buggy.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size_buggy = L_buggy.size();
//                System.out.println("size: " + size);
//                System.out.println("[buggy] size: " + size_buggy);
                assertEquals(size, size_buggy);
            } else if (operationNumber == 2) {
                // getLast
                // Skipping if size is zero.
                if (L.size() != 0 & L_buggy.size() != 0) {
                    int last = L.getLast();
                    int last_buggy = L_buggy.getLast();
//                    System.out.println("getLast = " + last);
//                    System.out.println("[buggy] getLast = " + last_buggy);
                    assertEquals(last, last_buggy);
                }
            } else if (operationNumber == 3) {
                // removeLast
                // Skipping if size is zero.
                if (L.size() != 0 & L_buggy.size() != 0) {
                    int last = L.removeLast();
                    int last_buggy = L_buggy.removeLast();
//                    System.out.println("removeLast: " + last);
//                    System.out.println("[buggy] removeLast = " + last_buggy);
                    assertEquals(last, last_buggy);
                    int size = L.size();
                    int size_buggy = L_buggy.size();
//                    System.out.println("size: " + size);
//                    System.out.println("[buggy] size: " + size_buggy);
                    assertEquals(size, size_buggy);
                }
            }
        }
    }
}

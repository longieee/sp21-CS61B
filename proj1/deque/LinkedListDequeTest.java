package deque;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    public void getTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addFirst(10);
        lld1.addLast(20);
        lld1.addLast(30);
        lld1.addLast(40);

        /*
         * List : 10 | 20 | 30 | 40
         * Index:  0 |  1 |  2 |  3
         * Index:  0 | -3 | -2 | -1
         * */

        assertEquals("Should have the same value", 10, lld1.get(0), 0.0);
        assertEquals("Should have the same value", 20, lld1.get(1), 0.0);
        assertEquals("Should have the same value", 30, lld1.get(2), 0.0);
        assertEquals("Should have the same value", 40, lld1.get(-1), 0.0);
        assertEquals("Should have the same value", 30, lld1.get(-2), 0.0);

        assertNull(lld1.get(4));
    }

    @Test
    public void printTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        lld1.addFirst(10);
        lld1.addLast(20);
        lld1.addLast(30);

        String expected = "10 20 30 \n";
        lld1.printDeque();
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void isEqualTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld3 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld4 = new LinkedListDeque<>();
        LinkedListDeque<Double> lld5 = new LinkedListDeque<>();
        LinkedListDeque<String> lld6 = new LinkedListDeque<>();

        lld1.addFirst(1);
        lld1.addLast(2);
        lld1.addLast(3);

        lld2.addFirst(1);
        lld2.addLast(2);
        lld2.addLast(3);

        lld3.addFirst(1);
        lld3.addLast(2);
        lld3.addLast(3);
        lld3.addLast(4);

        lld4.addFirst(1);
        lld4.addLast(2);
        lld4.addLast(4);

        lld5.addFirst(1.0);
        lld5.addLast(2.0);
        lld5.addLast(3.0);

        lld6.addFirst("1");
        lld6.addLast("2");
        lld6.addLast("3");

        assertTrue(lld1.equals(lld2));
        assertFalse(lld1.equals(lld3));
        assertFalse(lld1.equals(lld4));
        assertFalse(lld1.equals(lld5));
        assertFalse(lld1.equals(lld6));
    }

    @Test
    public void getRecursiveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addFirst(10);
        lld1.addLast(20);
        lld1.addLast(30);
        lld1.addLast(40);

        /*
         * List : 10 | 20 | 30 | 40
         * Index:  0 |  1 |  2 |  3
         * Index:  0 | -3 | -2 | -1
         * */

        assertEquals("Should have the same value", 10, lld1.getRecursive(0), 0.0);
        assertEquals("Should have the same value", 20, lld1.getRecursive(1), 0.0);
        assertEquals("Should have the same value", 30, lld1.getRecursive(2), 0.0);
        assertEquals("Should have the same value", 40, lld1.getRecursive(-1), 0.0);
        assertEquals("Should have the same value", 30, lld1.getRecursive(-2), 0.0);
        assertEquals("Should have the same value", 20, lld1.getRecursive(-3), 0.0);

        assertNull(lld1.get(4));
    }

    @Test
    public void hasNextAfterManyCalls() {
        Deque<Integer> lld = new LinkedListDeque<>();

        int N = 5;
        for (int i = 0; i < N; i++) {
            lld.addLast(i);
        }

        Iterator<Integer> iter = lld.iterator();
        for (int i = 0; i < N + 1; i++) {
            if (i < N - 1) {
                assertTrue(iter.hasNext());
                iter.next();
            } else {
                assertFalse(iter.hasNext());
            }
        }
    }
}

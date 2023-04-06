package deque;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    Comparator<Integer> defaultComparator = Integer::compareTo;

    @Test
    /* Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addTest() {
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque<>(defaultComparator);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());
        ad1.addFirst(1);
        assertFalse("ad1 should not be empty now", ad1.isEmpty());
        assertEquals("Size should be 1", 1, ad1.size());
        ad1.addFirst(2);
        assertEquals("Size should be 2", 2, ad1.size());
        ad1.addLast(3);
        assertEquals("Size should be 3", 3, ad1.size());

        String expected = "[2 1 3 ]\n";
        ad1.printDeque();
        assertEquals("Deque should be '2 1 3'", expected, outContent.toString());
    }

    @Test
    public void addRemoveTest() {
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque<>(defaultComparator);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());
        ad1.addLast(1);
        assertFalse("ad1 should not be empty now", ad1.isEmpty());
        assertEquals("Size should be 1", 1, ad1.size());
        ad1.addLast(2);
        assertEquals("Size should be 2", 2, ad1.size());
        ad1.addLast(3);
        assertEquals("Size should be 3", 3, ad1.size());
        ad1.addLast(4);
        assertEquals("Size should be 4", 4, ad1.size());

        int removed = ad1.removeLast();
        assertEquals("Size should be 3", 3, ad1.size());
        assertEquals("Removed item should be 4", 4, removed);
        removed = ad1.removeFirst();
        assertEquals("Size should be 2", 2, ad1.size());
        assertEquals("Removed item should be 1", 1, removed);

        ad1.printDeque();
        String expected = "[2 3 ]\n";
        assertEquals("Deque should be '[2 3 ]'", expected, outContent.toString());

        removed = ad1.removeLast();
        assertEquals("Size should be 1", 1, ad1.size());
        assertEquals("Removed item should be 3", 3, removed);

        removed = ad1.removeLast();
        assertEquals("Size should be 0", 0, ad1.size());
        assertEquals("Removed item should be 2", 2, removed);

        assertNull("Removed item should be null", ad1.removeLast());
    }

    @Test
    public void getTest() {
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque<>(defaultComparator);

        /* Array: 2  1  3  4
         * Index:  0  1  2  3
         * Index:  0 -3 -2 -1
         * */

        ad1.addFirst(1);
        ad1.addFirst(2);
        ad1.addLast(3);
        ad1.addLast(4);

        assertEquals("Index 0 should be 2", 2, (int) ad1.get(0));
        assertEquals("Index 1 should be 1", 1, (int) ad1.get(1));
        assertEquals("Index 2 should be 3", 3, (int) ad1.get(2));
        assertEquals("Index 3 should be 4", 4, (int) ad1.get(3));
        assertEquals("Index -3 should be 1", 1, (int) ad1.get(-3));
        assertEquals("Index -2 should be 3", 3, (int) ad1.get(-2));
        assertEquals("Index -1 should be 4", 4, (int) ad1.get(-1));
    }

    @Test
    public void resizeTestSimple() {
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque<>(defaultComparator);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Simple case: No loop
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(10);

        /* Array: 1 2 3 4 5 6 7 8 10 */
        assertEquals("Size should be 9", 9, ad1.size());

        ad1.printDeque();
        String expected = "[1 2 3 4 5 6 7 8 10 ]\n";
        assertEquals(
            "Deque should be '[1 2 3 4 5 6 7 8 10 ]'",
            expected,
            outContent.toString()
        );


    }

    @Test
    public void resizeTestComplex() {
        // Complex case: Looped
        MaxArrayDeque<Integer> ad2 = new MaxArrayDeque<>(defaultComparator);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Complex case: Looped index
        ad2.addFirst(1);    // 0
        ad2.addFirst(2);    // 7
        ad2.addFirst(3);    // 6
        ad2.addLast(4);     // 1
        ad2.addLast(5);     // 2
        ad2.addLast(6);     // 3
        ad2.addLast(7);     // 4
        ad2.addLast(8);     // 5
        ad2.addLast(10);    // Resize. Actual index in new array: 8

        /* Array: 1 2 3 4 5 6 7 8 10 */
        assertEquals("Size should be 9", 9, ad2.size());

        ad2.printDeque();
        String expected = "[3 2 1 4 5 6 7 8 10 ]\n";
        assertEquals("Deque should be '[3 2 1 4 5 6 7 8 10 ]'", expected, outContent.toString());
    }

    @Test
    public void bigArrayDequeTest() {
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque<>(defaultComparator);
        for (int i = 0; i < 1000000; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }
    }

    @Test
    public void arrayEqualsTest() {
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque<>(defaultComparator);
        MaxArrayDeque<Integer> ad2 = new MaxArrayDeque<>(defaultComparator);
        MaxArrayDeque<Integer> ad3 = new MaxArrayDeque<>(defaultComparator);
        MaxArrayDeque<Integer> ad4 = new MaxArrayDeque<>(defaultComparator);
        Integer[] wrong_type = new Integer[]{2, 1, 3, 4};

        /* Array: 2 1 3 4 */
        ad1.addLast(2);
        ad1.addLast(1);
        ad1.addLast(3);
        ad1.addLast(4);

        ad2.addFirst(1);
        ad2.addFirst(2);
        ad2.addLast(3);
        ad2.addLast(4);

        // 1 2 3 4
        ad3.addLast(1);
        ad3.addLast(2);
        ad3.addLast(3);
        ad3.addLast(4);

        // 1 2 3 4 5
        ad4.addLast(1);
        ad4.addLast(2);
        ad4.addLast(3);
        ad4.addLast(4);
        ad4.addLast(5);

        assertTrue(ad1.equals(ad2));
        assertFalse(ad1.equals(wrong_type));
        assertFalse(ad1.equals(ad3));
        assertFalse(ad1.equals(ad4));
    }

    @Test
    public void testMax() {
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque<>(defaultComparator);
        assertNull("max should return null when array is empty", ad1.max());
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addLast(1);
        assertFalse("ad1 should not be empty now", ad1.isEmpty());
        assertEquals("Size should be 1", 1, ad1.size());
        ad1.addLast(2);
        assertEquals("Size should be 2", 2, ad1.size());
        ad1.addLast(3);
        assertEquals("Size should be 3", 3, ad1.size());
        ad1.addLast(4);
        assertEquals("Size should be 4", 4, ad1.size());

        assertEquals("max should be 4", 4, (int) ad1.max());
    }
}

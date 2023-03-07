package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes() {
        IntList lst = IntList.of(1,2,3,4,5,6,7,8,9,10);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 4 -> 9 -> 4 -> 25 -> 6 -> 49 -> 8 -> 9 -> 10", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesOneElementList() {
        IntList lst = IntList.of(3);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("9", lst.toString());
        assertTrue(changed);
    }

    /* All primes in list */
    @Test
    public void testSquarePrimesAllPrimes() {
        IntList lst = IntList.of(2,3,5,7,11,13,17,19,23,29);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 9 -> 25 -> 49 -> 121 -> 169 -> 289 -> 361 -> 529 -> 841", lst.toString());
        assertTrue(changed);
    }

    /* No prime in list */
    @Test
    public void testSquarePrimesNoPrimes() {
        IntList lst = IntList.of(4,6,8,10,12,14,16,18,20);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 6 -> 8 -> 10 -> 12 -> 14 -> 16 -> 18 -> 20", lst.toString());
        assertFalse(changed);
    }
}

package DebugExercise;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DebugExercise2Test {
    @Test
    public void testSumOfElementwiseMaxes() {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] b = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int expected = 55;
        int actual = DebugExercise2.sumOfElementwiseMaxes(a,b);
        assertEquals(expected, actual);

        int[] a2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] b2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int expected2 = 0;
        int actual2 = DebugExercise2.sumOfElementwiseMaxes(a2,b2);
        assertEquals(expected2, actual2);
    }
}

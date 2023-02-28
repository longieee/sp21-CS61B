/** Class that prints the Collatz sequence starting from a given number.
 *  @author longiee
 */
public class Collatz {
    /** Function to calculate the next number in the Collatz sequence
     * If the number is even, the next number if n/2
     * If the number is odd, the next number is 3n+1
     */
    public static int nextNumber(int n) {
        if ((n%2) == 0) {
            return n/2;
        } else {
            return 3*n+1;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}


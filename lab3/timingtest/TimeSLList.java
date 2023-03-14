package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        int[] data_structure_size = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        int number_of_getLast_calls = 10000;

        /* Perform the timing tests */
        for (int size : data_structure_size) {
            SLList<Integer> L = new SLList<>();
            int i = 0, j=0;
            Ns.addLast(size);
            opCounts.addLast(number_of_getLast_calls);
            while (i < size) {
                L.addLast(i);
                i++;
            }
            Stopwatch sw = new Stopwatch();
            while (j<number_of_getLast_calls) {
                L.getLast();
                j++;
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }

        /* Print out the result table */
        printTimingTable(Ns, times, opCounts);
    }

}

package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class RandomizedDequeTest {
    @Test
    public void basicComparisonTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        Deque<Integer> lld1 = new LinkedListDeque<>();
        int N = 50;

        // Add random elements to deque
        for (int i = 0; i < N; i++) {
            int addedElement = StdRandom.uniform(N);
            ad1.addLast(addedElement);
            lld1.addLast(addedElement);
//            System.out.println("Added: " + addedElement);
        }

        assertTrue("LinkedListDeque and ArrayDeque with the same elements should be equal", ad1.equals(lld1));
        assertTrue("LinkedListDeque and ArrayDeque with the same elements should be equal", lld1.equals(ad1));
    }

    @Test
    public void largeComparisonTest() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        Deque<Integer> lld1 = new LinkedListDeque<>();
        int N = 20000;
        // Add random elements to deque
        for (int i = 0; i < N; i++) {
            int addedElement = StdRandom.uniform(N);
            ad1.addLast(addedElement);
            lld1.addLast(addedElement);
        }
        assertTrue("LinkedListDeque and ArrayDeque with the same elements should be equal", ad1.equals(lld1));
        assertTrue("LinkedListDeque and ArrayDeque with the same elements should be equal", lld1.equals(ad1));
    }

    @Test
    public void basicAddFirstRemoveLastAD() {
        Deque<Integer> ad = new ArrayDeque<>();

        int N = 20000; // Number of operations
        for (int i = 0; i < N; i++) {
            ad.addFirst(i);
        }
        for (int i = 0; i < N; i++) {
            int removed = ad.removeLast();
            assertEquals(i, removed);
        }
    }

    @Test
    public void randomizedAddRemoveEmptyLLD() {
        Deque<Integer> lld = new LinkedListDeque<>();

        int first = 0;
        int last = 0;
        int size_from_test = 0;

        int N = 100000;  // Number of operations
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);

            if (operationNumber == 0) {
                first = StdRandom.uniform(N);
                if (size_from_test == 0) {
                    last = first;
                }
                lld.addFirst(first);
                size_from_test++;
//                System.out.println("LinkedListDeque.addFirst(" + first + ")");
            } else if (operationNumber == 1) {
                last = StdRandom.uniform(N);
                if (size_from_test == 0) {
                    first = last;
                }
                lld.addLast(last);
                size_from_test++;
//                System.out.println("LinkedListDeque.addLast(" + last + ")");
            } else if (operationNumber == 2) {
                if (size_from_test == 0) {
                    assertTrue(lld.isEmpty());
                } else {
                    assertFalse(lld.isEmpty());
                }
            } else if (operationNumber == 3) {
                if (lld.size() == 0) {
                    continue;
                }
                int firstRemoved = lld.removeFirst();
//                System.out.println("LinkedListDeque.removeFirst()     ==> " + firstRemoved);
                assertEquals(first, firstRemoved);
                if (lld.size() > 0) {
                    first = lld.get(0);
                } else {
                    first = 0;
                }
                size_from_test--;
            } else {
                if (lld.size() == 0) {
                    continue;
                }
                int lastRemoved = lld.removeLast();
//                System.out.println("LinkedListDeque.removeLast()      ==> " + lastRemoved);
                assertEquals(last, lastRemoved);
                if (lld.size() > 0) {
                    last = lld.get(-1);
                } else {
                    last = 0;
                }
                size_from_test--;
            }
        }
    }

    @Test
    public void basicAddFirstRemoveLastMAD() {
        Comparator<Integer> defaultComparator = Integer::compareTo;
        Deque<Integer> mad = new MaxArrayDeque<>(defaultComparator);

        int N = 20000; // Number of operations
        for (int i = 0; i < N; i++) {
            mad.addFirst(i);
        }
        for (int i = 0; i < N; i++) {
            int removed = mad.removeLast();
            assertEquals(i, removed);
        }
    }

    @Test
    public void randomizedAddRemoveEmptyAD() {
        Deque<Integer> ad = new ArrayDeque<>();

        int first = 0;
        int last = 0;
        int size_from_test = 0;

        int N = 100000;  // Number of operations
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);

            if (operationNumber == 0) {
                first = StdRandom.uniform(N);
                if (size_from_test == 0) {
                    last = first;
                }
                ad.addFirst(first);
                size_from_test++;
//                System.out.println("ArrayDeque.addFirst(" + first + ")");
            } else if (operationNumber == 1) {
                last = StdRandom.uniform(N);
                if (size_from_test == 0) {
                    first = last;
                }
                ad.addLast(last);
                size_from_test++;
//                System.out.println("ArrayDeque.addLast(" + last + ")");
            } else if (operationNumber == 2) {
                if (size_from_test == 0) {
                    assertTrue(ad.isEmpty());
                } else {
                    assertFalse(ad.isEmpty());
                }
            } else if (operationNumber == 3) {
                if (ad.size() == 0) {
                    continue;
                }
                int firstRemoved = ad.removeFirst();
//                System.out.println("ArrayDeque.removeFirst()     ==> " + firstRemoved);
                assertEquals(first, firstRemoved);
                if (ad.size() > 0) {
                    first = ad.get(0);
                } else {
                    first = 0;
                }
                size_from_test--;
            } else {
                if (ad.size() == 0) {
                    continue;
                }
                int lastRemoved = ad.removeLast();
//                System.out.println("ArrayDeque.removeLast()      ==> " + lastRemoved);
                assertEquals(last, lastRemoved);
                if (ad.size() > 0) {
                    last = ad.get(-1);
                } else {
                    last = 0;
                }
                size_from_test--;
            }
        }
    }

    @Test
    public void randomizedAddRemoveEmptyMAD() {
        Comparator<Integer> defaultComparator = Integer::compareTo;
        Deque<Integer> mad = new MaxArrayDeque<>(defaultComparator);

        int first = 0;
        int last = 0;
        int size_from_test = 0;

        int N = 100000;  // Number of operations
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);

            if (operationNumber == 0) {
                first = StdRandom.uniform(N);
                if (size_from_test == 0) {
                    last = first;
                }
                mad.addFirst(first);
                size_from_test++;
//                System.out.println("MaxArrayDeque.addFirst(" + first + ")");
            } else if (operationNumber == 1) {
                last = StdRandom.uniform(N);
                if (size_from_test == 0) {
                    first = last;
                }
                mad.addLast(last);
                size_from_test++;
//                System.out.println("MaxArrayDeque.addLast(" + last + ")");
            } else if (operationNumber == 2) {
                if (size_from_test == 0) {
                    assertTrue(mad.isEmpty());
                } else {
                    assertFalse(mad.isEmpty());
                }
            } else if (operationNumber == 3) {
                if (mad.size() == 0) {
                    continue;
                }
                int firstRemoved = mad.removeFirst();
//                System.out.println("MaxArrayDeque.removeFirst()     ==> " + firstRemoved);
                assertEquals(first, firstRemoved);
                if (mad.size() > 0) {
                    first = mad.get(0);
                } else {
                    first = 0;
                }
                size_from_test--;
            } else {
                if (mad.size() == 0) {
                    continue;
                }
                int lastRemoved = mad.removeLast();
//                System.out.println("MaxArrayDeque.removeLast()      ==> " + lastRemoved);
                assertEquals(last, lastRemoved);
                if (mad.size() > 0) {
                    last = mad.get(-1);
                } else {
                    last = 0;
                }
                size_from_test--;
            }
        }
    }
}

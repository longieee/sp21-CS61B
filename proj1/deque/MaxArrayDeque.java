package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    /* Creates an empty array */
    public MaxArrayDeque(Comparator<T> c) {
        items = (T[]) new Object[8];
        nextFirst = 7;
        size = 0;
        comparator = c;
    }

    public T max() {
        if (size == 0) {
            return null;
        }
        int maxDex = 0;
        for (int i = 0; i < size; i ++) {
            int cmp = comparator.compare(this.get(i), this.get(maxDex));
            if (cmp > 0) {
                maxDex = i;
            }
        }
        return this.get(maxDex);
    }

    public T max(Comparator<T> c) {
        if (size == 0) {
            return null;
        }
        int maxDex = 0;
        for (int i = 0; i < items.length; i++) {
            int cmp = c.compare(this.get(i), this.get(maxDex));
            if (cmp > 0) {
                maxDex = i;
            }
        }
        return this.get(maxDex);
    }
}

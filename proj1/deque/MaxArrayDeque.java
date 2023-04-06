package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    /* Creates an empty array */
    public MaxArrayDeque(Comparator<T> c) {
        T[] madItems = (T[]) new Object[8];
        setItems(madItems);
        int madNextFirst = 7;
        setNextFirst(madNextFirst);
        int madSize = 0;
        setSize(madSize);
        comparator = c;
    }

    public T max() {
        if (super.size() == 0) {
            return null;
        }
        int maxDex = 0;
        for (int i = 0; i < super.size(); i++) {
            int cmp = comparator.compare(this.get(i), this.get(maxDex));
            if (cmp > 0) {
                maxDex = i;
            }
        }
        return this.get(maxDex);
    }

    public T max(Comparator<T> c) {
        if (super.size() == 0) {
            return null;
        }
        int maxDex = 0;
        for (int i = 0; i < super.size(); i++) {
            int cmp = c.compare(this.get(i), this.get(maxDex));
            if (cmp > 0) {
                maxDex = i;
            }
        }
        return this.get(maxDex);
    }
}

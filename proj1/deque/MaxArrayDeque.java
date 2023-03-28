package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> implements Iterable<T> {
    private T[] items;
    // what array indices hold the Deque’s front and back elements
    private int nextFirst;
    private int nextLast;   // Default is 0
    private int size;
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

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] new_array = (T[]) new Object[capacity];
//        int start_copy_index = size == _capacity ?
        // Copy items from old array to new bigger array
        for (int i = 0; i < size; i++) {
            new_array[i] = this.get(i);
        }
        items = new_array;
        // Reset index. The new array will always be simple case
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /* Scale up array if needed */
    private void upsize_if_needed() {
        if (size == items.length) {
            resize(size * 2);
        }
    }

    private void downsize_if_needed() {
        if ((items.length >= 16) & (size < (int) (items.length * 0.25))) {
            resize((int) (items.length * 0.25));
        }
    }

    public void addFirst(T item) {
        upsize_if_needed();
        items[nextFirst] = item;
        nextLast = nextFirst > nextLast ? nextLast : nextLast+1;
        nextFirst = nextFirst==0 ? items.length -1 : nextFirst-1;

        size++;
    }

    public void addLast(T item) {
        upsize_if_needed();
        items[nextLast++] = item;
        size++;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(this.get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) { return null; }
        nextFirst = Math.floorMod(++nextFirst, items.length);
        T firstItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        downsize_if_needed();
        return firstItem;
    }

    public T removeLast() {
        if (size == 0) { return null; }
        nextLast = Math.floorMod(--nextLast, items.length);
        T lastItem = items[nextLast];
        items[nextLast] = null;
        size--;
        downsize_if_needed();
        return lastItem;
    }

    public T get(int index) {
        if (index >= 0) {
            // Does not need Math.floorMod because all the numbers are positive
            return items[(((nextFirst + 1) % items.length) + index) % items.length];
        } else {
            return items[Math.floorMod(nextLast + index, items.length)];
        }
    }

    public Iterator<T> iterator() { return new MaxArrayDequeIterator(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o instanceof MaxArrayDeque compObj && compObj.size == this.size) { // Only compare if the object is another MaxArrayDeque
            for (int i = 0; i < size; i++) {
                if (this.get(i) != compObj.get(i)) { return false; }
            }
        } else { return false; }
        return true;
    }


    private class MaxArrayDequeIterator implements Iterator<T> {
        int currentPos = 0;    // The current element we're looking at
        // return whether there are more elements in the array that
        // have not been iterated over.
        public boolean hasNext() {
            return currentPos < size;
        }
        // return the next element of the iteration and move the current
        // index to the element after that.
        public T next() {
            return get(currentPos++);
        }
    }
}

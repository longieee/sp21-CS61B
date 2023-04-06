package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    // what array indices hold the Deque’s front and back elements
    private int nextFirst;
    private int nextLast;
    private int size;

    /* Creates an empty array */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 7;
        nextLast = 0;
        size = 0;
    }

    protected void setItems(T[] newItems) {
        items = newItems;
    }

    protected void setNextFirst(int newNextFirst) {
        nextFirst = newNextFirst;
    }

    protected void setSize(int newSize) {
        size = newSize;
    }

    /**
     * Resizes the underlying array to the target capacity.
     */
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        // Copy items from old array to new bigger array
        for (int i = 0; i < size; i++) {
            newArray[i] = this.get(i);
        }
        items = newArray;
        // Reset index. The new array will always be simple case
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /* Scale up array if needed */
    private void upsizeIfNeeded() {
        if (size == items.length) {
            resize(size * 2);
        }
    }

    private void downsizeIfNeeded() {
        if ((items.length >= 16) & (size < (int) (items.length * 0.25))) {
            resize((int) (items.length * 0.25));
        }
    }

    @Override
    public void addFirst(T item) {
        upsizeIfNeeded();
        items[Math.floorMod(nextFirst--, items.length)] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        upsizeIfNeeded();
        items[Math.floorMod(nextLast++, items.length)] = item;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        StringBuilder output = new StringBuilder();
        output.append('[');
        for (int i = 0; i < size; i++) {
            output.append(get(i).toString());
            output.append(' ');
        }
        output.append(']');
        System.out.println(output);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = Math.floorMod(++nextFirst, items.length);
        T firstItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        downsizeIfNeeded();
        return firstItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = Math.floorMod(--nextLast, items.length);
        T lastItem = items[nextLast];
        items[nextLast] = null;
        size--;
        downsizeIfNeeded();
        return lastItem;
    }

    @Override
    /* Get the item in the ArrayDeque by index */
    public T get(int index) {
        if (index >= 0) {
            // Does not need Math.floorMod because all the numbers are positive
            return items[Math.floorMod((((nextFirst + 1) % items.length) + index), items.length)];
        } else {
            return items[Math.floorMod(nextLast + index, items.length)];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        // Only compare if the object is another Deque
        if (o instanceof Deque && ((Deque<?>) o).size() == size) {
            Deque<T> compObj = (Deque<T>) o;
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(compObj.get(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {
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

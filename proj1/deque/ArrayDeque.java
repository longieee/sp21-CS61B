package deque;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    // what array indices hold the Deque’s front and back elements
    private int nextFirst;
    private int nextLast;
    private int size;

    /* Creates an empty array */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 7;
        size = 0;
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
        List<String> listOfItems = new ArrayList<>();
        for (int i=0; i<size; i++) {
            listOfItems.add(get(i).toString());
        }
        String output = "[" +
                String.join(" ", listOfItems) +
                "]";
        System.out.println(output);
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = Math.floorMod(++nextFirst, items.length);
        T firstItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        downsize_if_needed();
        return firstItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = Math.floorMod(--nextLast, items.length);
        T lastItem = items[nextLast];
        items[nextLast] = null;
        size--;
        downsize_if_needed();
        return lastItem;
    }

    /* Get the item in the ArrayDeque by index */
    public T get(int index) {
        if (index >= 0) {
            // Does not need Math.floorMod because all the numbers are positive
            return items[(((nextFirst + 1) % items.length) + index) % items.length];
        } else {
            return items[Math.floorMod(nextLast + index, items.length)];
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o instanceof ArrayDeque compObj && compObj.size == this.size) { // Only compare if the object is another ArrayDeque
            for (int i=0; i<size; i++) {
                if (this.get(i) != compObj.get(i)) {
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

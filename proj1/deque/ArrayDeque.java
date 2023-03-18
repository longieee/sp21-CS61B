package deque;

public class ArrayDeque<T> {
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
        for (int i = 0; i < size; i++) {
            System.out.print(this.get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        nextFirst = (++nextFirst) % items.length;
        T firstItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        downsize_if_needed();
        return firstItem;
    }

    public T removeLast() {
        nextLast = (--nextLast) % items.length;
        T lastItem = items[nextLast];
        items[nextLast] = null;
        size--;
        downsize_if_needed();
        return lastItem;
    }

    public T get(int index) {
        return items[(((nextFirst + 1) % items.length) + index) % items.length];
    }

//    public Iterator<T> iterator() {
//        return new ArrayDequeIterator();
//    }

    public boolean equals(Object o) {
        boolean is_equal = true;
        if (o instanceof ArrayDeque && ((ArrayDeque<?>) o).size == this.size) { // Only compare if the object is another LinkedListDeque
            for (int i = 0; i < size; i++) {
                try {
                    if (this.get(i) != ((ArrayDeque<?>) o).get(i)) {
                        is_equal = false;
                        break;
                    }
                } catch (Exception e) {
                    is_equal = false;
                    break;
                }
            }
        } else {
            is_equal = false;
        }
        return is_equal;
    }

//    private class ArrayDequeIterator implements Iterator<T> {
//        T current = null;    // The current element we're looking at
//        // return whether there are more elements in the array that
//        // have not been iterated over.
//        public boolean hasNext() {
//            return false;
//        }
//        // return the next element of the iteration and move the current
//        // index to the element after that.
//        public T next() {
//            if (!hasNext()) {
//                throw new NoSuchElementException();
//            }
//            T return_value = current.item;
//            current = current.next;
//            return return_value;
//        }
//    }
}

package deque;

import java.util.Iterator;

import static java.lang.Math.abs;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    /* If the first item exists, then it's at sentinel.next */
    private final ItemNode sentinel;
    private int size;

    /* Constructor creating an empty list */
    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null);
        // There's nothing in the list yet, so everything just points to sentinel
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /* Return the size of the list */
    @Override
    public int size() {
        return size;
    }

    /* add value of type Item to the first position of the list */
    @Override
    public void addFirst(T value) {
        ItemNode first = sentinel.next;
        ItemNode addedNode = new ItemNode(value, first, sentinel);
        sentinel.next = addedNode;
        first.prev = addedNode;
        size++;
    }

    /* add value of type Item to the last position of the list */
    @Override
    public void addLast(T value) {
        ItemNode last = sentinel.prev;
        ItemNode addedNode = new ItemNode(value, sentinel, last);
        sentinel.prev = addedNode;
        last.next = addedNode;
        size++;
    }

    /* Remove the first item in the list, if it's not empty */
    @Override
    public T removeFirst() {
        ItemNode first = sentinel.next;
        T returnValue = first.item;
        // Cannot remove anything if there's no item in the list
        if (size > 0) {
            // sentinel.next should now point to the second item
            sentinel.next = first.next;
            // second_item.prev is now sentinel
            first.next.prev = sentinel;
            // remove first to save memory
            first = null;
            size--;
        }
        return returnValue;
    }

    /* Remove the list item in the list, if it's not empty */
    @Override
    public T removeLast() {
        ItemNode last = sentinel.prev;
        T returnValue = last.item;
        // Cannot remove anything if there's no item in the list
        if (size > 0) {
            // sentinel.prev should now point to the second-to-last item
            sentinel.prev = last.prev;
            // second_to_last_item.next is now sentinel
            last.prev.next = sentinel;
            // remove last to save memory
            last = null;
            size--;
        }
        return returnValue;
    }

    @Override
    public T get(int index) {
        T returnItem = null;
        ItemNode current = sentinel;
        // Return null if index out-of-bound
        if (abs(index) < size) {
            // Walk index-number-of-steps + 1 (because we start from sent.) along the list to get where we needed
            // Forward walking
            if (index >= 0) {
                for (int i = 0; i <= index; i++) {
                    current = current.next;
                }
            } else {    // Backwards walking
                for (int i = 0; i > index; i--) {
                    current = current.prev;
                }
            }
            returnItem = current.item;
        }
        return returnItem;
    }

    /* same as get, but use recursion
     * This is a public interface
     * */
    public T getRecursive(int index) {
        // Return null if index out-of-bound
        if (abs(index) > size) {
            return null;
        }

        // Handles forward and backward indexing
        if (index >= 0) {
            return getRecursive(sentinel.next, index);
        } else {
            return getRecursive(sentinel.prev, index + 1);
        }

    }

    /* Actual recursion implementation of get */
    private T getRecursive(ItemNode node, int index) {
        if (index == 0) {
            return node.item;
        } else if (index < 0) {
            return getRecursive(node.prev, index + 1);
        } else {
            return getRecursive(node.next, index - 1);
        }
    }

    @Override
    public void printDeque() {
        ItemNode current = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        // New line when we finish printing the deque
        System.out.println();
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        // Only compare if the object is another LinkedListDeque
        if (o instanceof LinkedListDeque && ((LinkedListDeque<?>) o).size == this.size) {
            for (int i = 0; i < size; i++) {
                if (this.get(i) != ((LinkedListDeque<?>) o).get(i)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private class ItemNode {
        public ItemNode prev;
        public T item;
        public ItemNode next;

        public ItemNode(T nodeItem, ItemNode next_node, ItemNode prev_node) {
            item = nodeItem;
            next = next_node;
            prev = prev_node;
        }
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        ItemNode current = sentinel.next;    // The current element we're looking at

        // return whether there are more elements in the array that
        // have not been iterated over.
        public boolean hasNext() {
            return current.next != sentinel;
        }

        // return the next element of the iteration and move the current
        // index to the element after that.
        public T next() {
            T returnValue = current.item;
            current = current.next;
            return returnValue;
        }
    }

}

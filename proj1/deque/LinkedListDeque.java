package deque;

import java.util.Iterator;

import static java.lang.Math.abs;

public class LinkedListDeque<T> implements Deque<T> {
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
    public int size() {
        return size;
    }

    /* add value of type Item to the first position of the list */
    public void addFirst(T value) {
        ItemNode first = sentinel.next;
        ItemNode added_node = new ItemNode(value, first, sentinel);
        sentinel.next = added_node;
        first.prev = added_node;
        size++;
    }

    /* add value of type Item to the last position of the list */
    public void addLast(T value) {
        ItemNode last = sentinel.prev;
        ItemNode added_node = new ItemNode(value, sentinel, last);
        sentinel.prev = added_node;
        last.next = added_node;
        size++;
    }

    /* Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Remove the first item in the list, if it's not empty */
    public T removeFirst() {
        ItemNode first = sentinel.next;
        T return_value = first.item;
        // Cannot remove anything if there's no item in the list
        if (size>0) {
            // sentinel.next should now point to the second item
            sentinel.next = first.next;
            // second_item.prev is now sentinel
            first.next.prev = sentinel;
            // remove first to save memory
            first = null;
            size--;
        }
        return return_value;
    }

    /* Remove the list item in the list, if it's not empty */
    public T removeLast() {
        ItemNode last = sentinel.prev;
        T return_value = last.item;
        // Cannot remove anything if there's no item in the list
        if (size>0) {
            // sentinel.prev should now point to the second-to-last item
            sentinel.prev = last.prev;
            // second_to_last_item.next is now sentinel
            last.prev.next = sentinel;
            // remove last to save memory
            last = null;
            size--;
        }
        return return_value;
    }

    public T get(int index) {
        T return_item = null;
        ItemNode current = sentinel;
        // Return null if index out-of-bound
        if (abs(index)<size) {
            // Walk index-number-of-steps + 1 (because we start from sent.) along the list to get where we needed
            // Forward walking
            if (index>=0) {
                for (int i = 0; i <= index; i++) {
                    current = current.next;
                }
            } else {    // Backwards walking
                for (int i = 0; i > index; i--) {
                    current = current.prev;
                }
            }
            return_item = current.item;
        }
        return return_item;
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
            return getRecursive(sentinel.prev, index+1);
        }

    }

    /* Actual recursion implementation of get */
    private T getRecursive(ItemNode node, int index) {
        if (index == 0) {
            return node.item;
        } else if (index < 0) {
            return getRecursive(node.prev,index+1);
        } else {
            return getRecursive(node.next,index-1);
        }
    }

    public void printDeque() {
        ItemNode current = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        // New line when we finish printing the deque
        System.out.println();
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o instanceof LinkedListDeque && ((LinkedListDeque<?>) o).size == this.size) { // Only compare if the object is another LinkedListDeque
            for (int i=0; i<size; i++) {
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

        public ItemNode(T _item, ItemNode next_node, ItemNode prev_node) {
            item = _item;
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
            T return_value = current.item;
            current = current.next;
            return return_value;
        }
    }

}

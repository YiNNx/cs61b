/**
 * Double-ended queues are sequence containers with dynamic sizes
 * that can be expanded or contracted on both ends (either its front or its back).
 */
public class LinkedListDeque<T> {

    private int size;
    private final Node sentinel;

    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node() {

        }

        public Node(T data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public T getRecursive(int index) {
            if (index == 0) {
                return this.data;
            }
            return this.next.getRecursive(index - 1);
        }
    }

    // Creates an empty linked list deque.
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    // Adds an item of type T to the front of the deque.
    public void addFirst(T item) {
        Node l = new Node(item, this.sentinel.next, this.sentinel);
        this.sentinel.next.prev = l;
        this.sentinel.next = l;

        size = size + 1;
    }

    // Adds an item of type T to the back of the deque.
    public void addLast(T item) {
        Node l = new Node(item, this.sentinel, this.sentinel.prev);
        this.sentinel.prev.next = l;
        this.sentinel.prev = l;

        size = size + 1;
    }

    // Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        return this.sentinel.next == this.sentinel;
    }

    // Returns the number of items in the deque.
    public int size() {
        return this.size;
    }

    // Prints the items in the deque from first to last, separated by a space.
    public void printDeque() {
        for (Node cur = this.sentinel.next; cur != this.sentinel; cur = cur.next) {
            System.out.print(cur.data + " ");
        }
    }

    // Removes and returns the item at the front of the deque. If no such item
    // exists, returns null.
    // Do not maintain references to items that are no longer in the deque.
    public T removeFirst() {
        if(this.isEmpty()){
            return null;
        }

        T data = this.sentinel.next.data;
        this.sentinel.next.data = null;
        this.sentinel.next.next.prev = this.sentinel;
        this.sentinel.next = this.sentinel.next.next;

        size = size - 1;
        return data;
    }

    // Removes and returns the item at the front of the deque. If no such item
    // exists, returns null.
    // Do not maintain references to items that are no longer in the deque.
    public T removeLast() {
        if(this.isEmpty()){
            return null;
        }

        T data = this.sentinel.prev.data;
        this.sentinel.prev.data = null;
        this.sentinel.prev.prev.next = this.sentinel;
        this.sentinel.prev = this.sentinel.prev.prev;

        size = size - 1;
        return data;
    }

    // Gets the item at the given index, where 0 is the front, 1 is the next item,
    // and so forth. If no such item exists, returns null. Must not alter the deque!
    public T get(int index) {
        if (index >= this.size) {
            return null;
        }
        Node cur = this.sentinel.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    // Same as get, but uses recursion.
    public T getRecursive(int index) {
        if (index >= this.size || index < 0) {
            return null;
        }

        return this.sentinel.next.getRecursive(index);
    }
}

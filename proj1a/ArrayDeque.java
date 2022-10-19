public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int start;

    private final int RFACTOR = 2;
    private final int startingSize = 8;

    public ArrayDeque() {
        items = (T[]) new Object[startingSize];
        size = 0;
        start = 0;
    }

    private void resize(int size) {
        T[] newItems = (T[]) new Object[size];

        if (items.length - this.start >= this.size) {
            System.arraycopy(this.items, this.start, newItems, 0, this.size);
        } else {
            System.arraycopy(this.items, this.start, newItems, 0, items.length - this.start);
            System.arraycopy(
                    this.items, 0, newItems,
                    items.length - this.start,
                    this.size - (items.length - this.start)
            );
        }
        this.items = newItems;
        this.start = 0;
    }

    private int getPrev(int index) {
        return ((index - 1) % items.length + items.length) % items.length;
    }

    private int getNext(int index) {
        return (index + 1) % items.length;
    }

    private int getLast() {
        return (this.start + this.size - 1) % this.items.length;
    }

    private int getItemsIndex(int index) {
        return (this.start + index) % this.items.length;
    }

    // Adds an item of type T to the front of the deque.
    public void addFirst(T item) {
        if (this.size == items.length) {
            this.resize((items.length + 1) * RFACTOR);
        }

        this.start = this.getPrev(this.start);
        this.items[start] = item;
        size = size + 1;
    }

    // Adds an item of type T to the back of the deque.
    public void addLast(T item) {
        if (this.size == items.length) {
            this.resize((items.length + 1) * RFACTOR);
        }

        int next = this.getNext(this.getLast());
        this.items[next] = item;
        size = size + 1;
    }

    // Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Returns the number of items in the deque.
    public int size() {
        return this.size;
    }

    // Prints the items in the deque from first to last, separated by a space.
    public void printDeque() {
        int index = this.start;
        for (int i = 0; i <= size; i++) {
            System.out.print(this.items[index] + " ");
            index = this.getNext(index);
        }
    }

    // Removes and returns the item at the front of the deque. If no such item
    // exists, returns null.
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        if (this.size <= this.items.length / 4) {
            this.resize(items.length / 2);
        }

        T first = this.items[start];

        this.items[start] = null;
        start = this.getNext(start);
        size = size - 1;
        return first;
    }

    // Removes and returns the item at the back of the deque. If no such item
    // exists, returns null.
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        if (this.size <= this.items.length / 4) {
            this.resize(items.length / 2);
        }

        int lastIndex = this.getLast();
        T last = this.items[lastIndex];

        this.items[lastIndex] = null;
        size = size - 1;
        return last;
    }

    // Gets the item at the given index, where 0 is the front, 1 is the next item,
    // and so forth. If no such item exists, returns null. Must not alter the deque!
    public T get(int index) {
        if (index >= this.items.length || index < 0) {
            return null;
        }
        return this.items[this.getItemsIndex(index)];
    }


}

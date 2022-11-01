import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int initialSize = 16;
    private double loadFactor = 0.75;
    private Node<K, V>[] buckets;
    private int bucketsSize;
    private int itemsNum;
    Set<K> keySet;

    public MyHashMap() {
        this.buckets = new Node[this.initialSize];
        this.bucketsSize = this.initialSize;
        this.keySet = new HashSet<>();
    }

    public MyHashMap(int initSize) {
        this.initialSize = initSize;
        this.buckets = new Node[this.initialSize];
        this.bucketsSize = this.initialSize;
        this.keySet = new HashSet<>();
    }

    public MyHashMap(int initSize, double loadFac) {
        this.initialSize = initSize;
        this.loadFactor = loadFac;
        this.buckets = new Node[this.initialSize];
        this.bucketsSize = this.initialSize;
        this.keySet = new HashSet<>();
    }

    @Override
    public void clear() {
        keySet.clear();
        Arrays.fill(buckets, null);
        this.itemsNum = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    private static int getBucketIndex(int hashCode, int bucketsSize) {
        int res = hashCode % bucketsSize;
        if (res >= 0) return res;
        else return res + bucketsSize;
    }

    @Override
    public V get(K key) {
        int hash = key.hashCode();
        int i = getBucketIndex(hash, bucketsSize);
        if (this.buckets[i] == null) return null;
        else return this.buckets[i].get(key);
    }

    @Override
    public int size() {
        return this.itemsNum;
    }

    @Override
    public void put(K key, V value) {
        int hash = key.hashCode();
        int i = getBucketIndex(hash, bucketsSize);
        Node<K, V> n = new Node<>(key, value, hash);
        if (this.buckets[i] == null) {
            this.buckets[i] = n;
            this.itemsNum++;
        } else {
            if (this.buckets[i].add(n)) this.itemsNum++;
        }
        keySet.add(key);

        if ((double) this.itemsNum / (double) this.bucketsSize > loadFactor) {
            resize();
        }
    }

    private void resize() {
        int newSize = bucketsSize * 2;
        Node<K, V>[] newBuckets = new Node[newSize];
        for (K key : keySet) {
            V value = get(key);
            int hash = key.hashCode();
            int i = getBucketIndex(hash, newSize);
            Node<K, V> n = new Node<>(key, value, hash);
            if (newBuckets[i] == null) newBuckets[i] = n;
            else newBuckets[i].add(n);
        }
        this.buckets = newBuckets;
        this.bucketsSize = newSize;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    static private class Node<K, V> {
        private final K key;
        private V val;
        private Node<K, V> next;

        private Node(K key, V value, int hash) {
            this.key = key;
            this.val = value;
        }

        private boolean add(Node<K, V> n) {
            Node<K, V> cur = this;
            while (cur != null) {
                if (cur.key.equals(n.key)) {
                    cur.val = n.val;
                    return false;
                }
                cur=cur.next;
            }
            n.next=this.next;
            this.next=n;
            return true;
        }

        private V get(K key) {
            Node<K, V> cur = this;
            while (cur != null) {
                if (cur.key.equals(key)) return cur.val;
                cur = cur.next;
            }
            return null;
        }

    }

    @Override
    public Iterator<K> iterator() {
        return new mapIterator();
    }

    private class mapIterator implements Iterator<K>{
        Iterator<K> seer;

        public mapIterator(){
            seer=keySet.iterator();
        }

        @Override
        public boolean hasNext() {
            return seer.hasNext();
        }

        @Override
        public K next() {
            return seer.next();
        }
    }
}

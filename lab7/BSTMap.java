import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
public class BSTMap<K, V> implements Map61B<K, V> {

    Node<K, V> start;
    int size;

    private class Node<K, V> {
        K key;
        V val;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean isRed;

        public Node(K key, V val, Node<K, V> left, Node<K, V> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            this.parent = null;
            this.isRed = true;
        }

        public boolean hasParent() {
            return this.parent != null;
        }

        public boolean hasLeft() {
            return this.left != null;
        }

        public boolean hasRight() {
            return this.right != null;
        }

        private void connectLeft(Node<K, V> left) {
            this.left = left;
            left.parent = this;
        }

        private void connectRight(Node<K, V> right) {
            this.left = right;
            right.parent = this;
        }

        public boolean containsKey(K key) {
            if (key==this.key) return true;
            if()
            return false;
        }
    }

    private void rotateLeft(Node<K, V> N) {
        Node<K, V> R = N.right;
        Node<K, V> P = N.parent;
        if (R == null) return;
        if (P != null) {
            if (P.left == N) P.connectLeft(N.right);
            else P.connectRight(N.right);
        }
        N.connectRight(R.left);
        R.connectLeft(N);
    }

    private void rotateRight(Node<K, V> N) {
        Node<K, V> L = N.left;
        Node<K, V> P = N.parent;
        if (L == null) return;
        if (P != null) {
            if (P.left == N) P.connectLeft(N.left);
            else P.connectRight(N.left);
        }
        N.connectLeft(L.right);
        L.connectRight(N);
    }

    @Override
    public boolean containsKey(K key) {
        return start.containsKey(key);
    }


    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(K key, V value) {

    }

    public void printInOrder() {

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}

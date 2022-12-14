import java.util.Set;
import java.util.Iterator;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node sentinel;
    private int size;

    public BSTMap() {
        size = 0;
        sentinel = new Node(null, null);
        sentinel.changeToBlack();
    }

    private Node getRoot() {
        return sentinel.left;
    }

    public void printTree() {
        if (this.getRoot() == null) return;
        this.getRoot().printTree(getRoot(), "", false);
    }

    @Override
    public boolean containsKey(K key) {
        if (this.getRoot() == null) return false;
        return this.getRoot().contains(key);
    }

    @Override
    public V get(K key) {
        if (this.getRoot() == null) return null;
        return this.getRoot().gets(key);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        if (this.getRoot() == null) {
            Node n = new Node(key, value);
            n.changeToBlack();
            sentinel.connectLeft(n);
            ++this.size;
            return;
        }

        Node n = new Node(key, value);
        Node cur = this.getRoot();
        while (true) {
            int cmp = cur.compareTo(n);
            if (cmp == 0) {
                cur.val = n.val;
                break;
            } else if (cmp > 0) {
                if (cur.left == null) {
                    cur.connectLeft(n);
                    n.balance();
                    break;
                }
                cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.connectRight(n);
                    n.balance();
                    break;
                }
                cur = cur.right;
            }
        }
        ++this.size;
        this.getRoot().changeToBlack();
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


    private class Node {
        final private K key;
        private V val;
        private Node left;
        private Node right;
        private Node parent;
        private boolean isRed;

        private Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.isRed = true;
        }

        private boolean isRed() {
            return this.isRed;
        }

        private void changeToBlack() {
            this.isRed = false;
        }

        private void changeToRed() {
            this.isRed = true;
        }

        private void connectLeft(Node left) {
            this.left = left;
            if (left != null) {
                left.parent = this;
            }
        }

        private void connectRight(Node right) {
            this.right = right;
            if (right != null) {
                right.parent = this;
            }
        }

        private boolean contains(K key) {
            int cmp = this.key.compareTo(key);
            if (cmp == 0) return true;
            else if (cmp > 0) {
                if (this.left == null) return false;
                return this.left.contains(key);
            } else {
                if (this.right == null) return false;
                return this.right.contains(key);
            }
        }

        private int compareTo(Node N) {
            return this.key.compareTo(N.key);
        }

        private V gets(K key) {
            int cmp = this.key.compareTo(key);
            if (cmp == 0) return this.val;
            else if (cmp > 0) {
                if (this.left == null) return null;
                return this.left.gets(key);
            } else {
                if (this.right == null) return null;
                return this.right.gets(key);
            }
        }

        private void balance() {
            Node n = this;
            while (n.parent != sentinel && n.isRed()) {
                if (n.parent.right == n) { // right leaning
                    if (n.parent.left != null && n.parent.left.isRed()) // flip
                        n.parent.flipsColor();
                    else { // right lean rotates to left
                        n.parent.rotateLeft();
                        n = n.left;
                    }
                }
                if (n.parent.left == n) { // left leaning
                    if (n.parent.isRed() && n.parent.parent.left == n.parent) // two consecutive left red link
                        n.parent.parent.rotateRight();
                    if (n.parent.right != null && n.parent.right.isRed()) // flip
                        n.parent.flipsColor();
                }
                n = n.parent;
            }
        }

        private void flipsColor() {
            this.left.changeToBlack();
            this.right.changeToBlack();
            this.changeToRed();
        }

        private void rotateLeft() {
            Node R = this.right;
            Node P = this.parent;
            if (R == null) return;
            boolean isRedR = R.isRed, isRedN = this.isRed;
            if (P != null) {
                if (P.left == this) P.connectLeft(R);
                else P.connectRight(R);
                R.isRed = isRedN;
            }
            this.connectRight(R.left);
            R.connectLeft(this);
            this.isRed = isRedR;
        }

        private void rotateRight() {
            Node L = this.left;
            Node P = this.parent;
            if (L == null) return;
            boolean isRedL = L.isRed, isRedN = this.isRed;
            if (P != null) {
                if (P.left == this) P.connectLeft(this.left);
                else P.connectRight(this.left);
                L.isRed = isRedN;
            }
            this.connectLeft(L.right);
            L.connectRight(this);
            this.isRed = isRedL;
        }

        private void printTree(Node n, String indent, boolean print_leaf) {
            if (n == null) {
                System.out.print((print_leaf ? indent + "   |-+*\n" : ""));
            } else {
                indent += "   ";
                String color = n.isRed() ? "???" : "???";
                if (n.parent == sentinel) {
                    printTree(n.right, indent + " ", print_leaf);
                    System.out.print(indent + color + n.val + "\n");
                    printTree(n.left, indent + " ", print_leaf);
                } else if (n.parent.right == n) {
                    printTree(n.right, indent + " ", print_leaf);
                    System.out.print(indent + "|" + color + n.val + "\n");
                    printTree(n.left, indent + "|", print_leaf);
                } else {
                    printTree(n.right, indent + "|", print_leaf);
                    System.out.print(indent + "|" + color + n.val + "\n");
                    printTree(n.left, indent + " ", print_leaf);
                }
            }

        }
    }


}

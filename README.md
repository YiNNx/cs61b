# CS61B Record

## Lab 6: Unoin Find #sp19

Implement a Union Find structure, which support the following operations on a fixed number *N* of objects:

- `union(int p, int q)` Connects two elements v1 and v2 together.onnects two elements `v1` and `v2` together.
- `connected(int p, int q)` Returns true if nodes `v1` and `v2` are connected.

### Stucture

![image-20221101224439407](http://cdn.just-plain.fun/img/image-20221101224439407.png)

**Weighted Quick Union**: Whenever we call `connect`, always link the root of the **smaller** tree **to the larger** tree.

**Weighted quick union with path compression**: When `find` is called, every node along the way is made to point at the root. Results in nearly flat trees. Making M calls to union and find with N objects results in no more than O(Mlog∗N).

### Implement

```java
import java.util.Arrays;

public class UnionFind {
    private int[] parent;
    int size;

    // Creates a UnionFind data structure holding n vertices. Initially, all vertices are in disjoint sets.
    public UnionFind(int n) {
        this.parent = new int[n];
        this.size = n;
        Arrays.fill(parent, -1);
    }

    // Throws an exception if v1 is not a valid index.
    public void validate(int v1) {
        if (v1 >= this.size || v1 < 0) {
            throw new IllegalArgumentException();
        }
    }

    // Returns the size of the set v1 belongs to.
    public int sizeOf(int v1) {
        validate(v1);
        return -parent[find(v1)];
    }

    // Returns the parent of v1. If v1 is the root of a tree, returns the negative size of the tree for which v1 is the root.
    public int parent(int v1) {
        return parent[v1];
    }

    // Returns true if nodes v1 and v2 are connected.
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    // Connects two elements v1 and v2 together. v1 and v2 can be any valid elements, and a union-by-size heuristic is used. If the sizes of the sets are equal, tie break by connecting v1’s root to v2’s root. Unioning a vertex with itself or vertices that are already connected should not change the sets, but it may alter the internal structure of the data structure.
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        if (parent(root1) < parent(root2)) {
            parent[root1] = parent[root1] + parent[root2];
            parent[root2] = root1;
        } else {
            parent[root2] = parent[root2] + parent[root1];
            parent[root1] = root2;
        }
    }

    // Returns the root of the set v1 belongs to. Path-compression is employed allowing for fast search-time.
    public int find(int v1) {
        if (parent(v1) < 0) {
            return v1;
        }
        int root = find(parent(v1));
        parent[v1] = root;
        return root;
    }
}
```

## Lab 6 Challenge

[Leet Code #803](https://leetcode.cn/problems/bricks-falling-when-hit/)

逆序思维+Disjoint Set

```java
public class BubbleGrid {
    private static final int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    private UnionFind u;
    private int[][] grid;
    private int row, col, ceiling;

    public int[] popBubbles(int[][] g, int[][] darts) {
        int[] res = new int[darts.length];
        if (g == null || g[0] == null) {
            return res;
        }

        grid = g;
        row = grid.length;
        col = grid[0].length;
        u = new UnionFind(row * col + 1);
        ceiling = row * col;

        for (int i = darts.length - 1; i >= 0; i--) {
            if (grid[darts[i][0]][darts[i][1]] == 0) {
                res[i] = -1;
            } else {
                grid[darts[i][0]][darts[i][1]] = 0;
            }
        }

        for (int i = 0; i < col; i++) {
            unionCeiling(0, i);
        }
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                unionBubble(i, j, i + 1, j);
                unionBubble(i, j, i, j + 1);
            }
        }

        int ceilingNum = sizeofCeiling();

        for (int i = darts.length - 1; i >= 0; i--) {
            if (res[i] == -1) {
                res[i] = 0;
                continue;
            }

            int rowD = darts[i][0], colD = darts[i][1];
            boolean[] stucks = new boolean[4];
            boolean stuck = false;

            for (int m = 0; m < 4; m++) {
                if (!stucks[m] && (connectedCeiling(rowD + directions[m][0], colD + directions[m][1])))
                    stuck = stucks[m] = true;
            }

            grid[rowD][colD] = 1;
            if (rowD == 0) unionCeiling(rowD, colD);

            for (int m = 0; m < 4; m++) {
                unionBubble(rowD + directions[m][0], colD + directions[m][1], rowD, colD);
            }

            res[i] += sizeofCeiling() - ceilingNum;
            if(res[i]!=0) --res[i];
            ceilingNum = sizeofCeiling();
        }
        return res;
    }

    private void unionBubble(int x1, int y1, int x2, int y2) {
        if (!isBubble(x1, y1) || !isBubble(x2, y2)) return;
        u.union(unionIndex(x1, y1), unionIndex(x2, y2));
    }

    private void unionCeiling(int x, int y) {
        if (!isBubble(x, y)) return;
        u.union(unionIndex(x, y), ceiling);
    }

    private boolean connectedCeiling(int x, int y) {
        return (isBubble(x, y) && u.connected(unionIndex(x, y), ceiling));
    }

    private boolean isBubble(int x, int y) {
        return validPos(x, y) && grid[x][y] == 1;
    }

    private int sizeofCeiling() {
        return u.sizeOf(ceiling);
    }

    private int unionIndex(int x, int y) {
        return x * col + y;
    }

    private boolean validPos(int x, int y) {
        return x >= 0 && x < row && y >= 0 && y < col;
    }
}
```

## Lab 7: TreeMap #sp19

搓了个支持增改查的LLRB Tree

### Structure (LLRB)

**Red Black Tree** is a kind of balanced BST, which is based on **rotation operation** and is structurally identical to **B Tree**.

#### *ROTATION*

![image-20221101224748885](http://cdn.just-plain.fun/img/image-20221101224748885.png)

**retateLeft(G)**

- G's right child (must has)-> G's right parent
- G's parent (if has) -> (G's right child)'s parent
- (G's right child)'s left child (if has) -> G's right child

#### *BANLANCE*

- When inserting: use a red link.
- 3 Situations
  1. any nodes with 2 red children -> Color flips the node
  2. a right red link alone  -> rotateLeft()
  3. two consecutive left red link -> rotateRight()

#### *RUNTIME*

- Height -> O(log N)

- contains() -> O(log N)

  insert() -> O(log N)

  ( O(log N) to add new node, O(log N) to rotate and flip color)

### Implement

```java
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
        this.getRoot().printTree(getRoot(), "      ", true);
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
                String color = n.isRed() ? "●" : "○";
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
```

**Performance↓**

![image-20221101232339918](http://cdn.just-plain.fun/img/image-20221101232339918.png)

## Lab 7: HashMap

Implement HashMap

### Structure 

![image-20221101233337283](http://cdn.just-plain.fun/img/image-20221101233337283.png)

### Implement

```java
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
```

## Homework 3: Hashing

Implement `hashCode()`method for class `Oomega`

- `SimpleOomega.class`

```java
@Override
public int hashCode() {
    return red + green + blue;
}
```

↓

```java
@Override
public int hashCode() {
    if (!USE_PERFECT_HASH) {
        return red + green + blue;
    } else {
        return (red + 257 * green + 257 * 257 * blue) / 5;
    }
}
```

- `ComplexOomega.class`

```java
@Test
public void testWithDeadlyParams() {
    List<Oomage> deadlyList = new ArrayList<>();
    int N = 5000;
    int[] deadNums = new int[]{3, 4, 5, 6};

    for (int i = 0; i < N; i += 1) {
        int M = StdRandom.uniform(1, 10);
        ArrayList<Integer> params = new ArrayList<>(M);
        for (int j = 0; j < M; j += 1) {
            params.add(StdRandom.uniform(0, 255));
        }
        for (int k : deadNums) {
            params.add(k);
        }
        deadlyList.add(new ComplexOomage(params));
    }
    HashTableVisualizer.visualize(deadlyList, 10, 0.2);
    assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
}
```

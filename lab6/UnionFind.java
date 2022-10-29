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

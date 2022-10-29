import java.util.Arrays;

class Solution {
    public int[] hitBricks(int[][] grid, int[][] hits) {
        return popBubbles(grid, hits);
    }
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
                if (!stucks[m] && (connectedCeiling(rowD + directions[m][0], colD + directions[m][1])) ||
                        m == 0 && rowD == 0)
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

    private class UnionFind {
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


}

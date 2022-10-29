import java.util.Arrays;

class Solution {
    public int[] hitBricks(int[][] grid, int[][] hits) {
        return popBubbles(grid, hits);
    }
    static int row, col;
    static UnionFind u;

    public static int[] popBubbles(int[][] grid, int[][] darts) {
        int[] res = new int[darts.length];
        if (grid == null || grid[0] == null)
            return res;

        row = grid.length;
        col = grid[0].length;
        u = new UnionFind(col * row);

        for (int i = darts.length - 1; i >= 0; i--) {
            if (grid[darts[i][0]][darts[i][1]] == 0) {
                res[i] = -1;
            } else {
                grid[darts[i][0]][darts[i][1]] = 0;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int[] dot = new int[]{i, j}, dotR = new int[]{i, j + 1}, dotB = new int[]{i + 1, j};
                if (isBubble(dot, grid)) {
                    if (isBubble(dotB, grid)) unionU(dot, dotB);
                    if (isBubble(dotR, grid)) unionU(dot, dotR);
                }
            }
        }

        for (int i = darts.length - 1; i >= 0; i--) {
            if (res[i] == -1) {
                res[i] = 0;
                continue;
            }

            int rowD = darts[i][0], colD = darts[i][1];
            int[] dotL = new int[]{rowD, colD - 1}, dotR = new int[]{rowD, colD + 1}, dotT = new int[]{rowD - 1, colD}, dotB = new int[]{rowD + 1, colD};
            boolean stuckL = false, stuckR = false, stuckB = false, stuckT = false;

            for (int m = 0; m < col; m++) {
                int[] dotStuck = new int[]{0, m};
                if (!stuckR && isBubble(dotR, grid) && connectedU(dotStuck, dotR)) {
                    stuckR = true;
                }
                if (!stuckL && isBubble(dotL, grid) && connectedU(dotStuck, dotL)) {
                    stuckL = true;
                }
                if (!stuckB && isBubble(dotB, grid) && connectedU(dotStuck, dotB)) {
                    stuckB = true;
                }
                if (!stuckT && isBubble(dotT, grid) && connectedU(dotStuck, dotT)) {
                    stuckT = true;
                }
            }
            if (stuckB || stuckR || stuckT || stuckL) {
                if (!stuckB)
                    res[i] += sizeofU(dotB,grid);
                if (!stuckL && !connectedU(dotB, dotL))
                    res[i] += sizeofU(dotL,grid);
                if (!stuckR && !connectedU(dotR, dotB) && !connectedU(dotR, dotL))
                    res[i] += sizeofU(dotR,grid);
                if (!stuckT && !connectedU(dotT, dotB) && !connectedU(dotT, dotL) && !connectedU(dotT, dotR))
                    res[i] += sizeofU(dotT,grid);
            }
            unionU(darts[i], dotT);
            unionU(darts[i], dotB);
            unionU(darts[i], dotR);
            unionU(darts[i], dotL);
        }
        return res;
    }

    private static void unionU(int[] dot1, int[] dot2) {
        if (!validU(dot1) || !validU(dot2)) return;
        u.union(dot1[0] * col + dot1[1], dot2[0] * col + dot2[1]);
    }

    private static boolean connectedU(int[] dot1, int[] dot2) {
        return validU(dot1) && validU(dot2) && u.connected(dot1[0] * col + dot1[1], dot2[0] * col + dot2[1]);
    }

    private static boolean validU(int[] dot) {
        return (dot[0] >= 0 && dot[0] < row && dot[1] >= 0 && dot[1] < col);
    }

    private static boolean isBubble(int[] dot, int[][] grid) {
        return validU(dot) && grid[dot[0]][dot[1]] == 1;
    }

    private static int sizeofU(int[] dot, int[][] grid) {
        if (!isBubble(dot,grid)) return 0;
        return u.sizeOf(dot[0] * col + dot[1]);
    }

}
//
//class UnionFind {
//    private int[] parent;
//    int size;
//
//    // Creates a UnionFind data structure holding n vertices. Initially, all vertices are in disjoint sets.
//    public UnionFind(int n) {
//        this.parent = new int[n];
//        this.size = n;
//        Arrays.fill(parent, -1);
//    }
//
//    // Throws an exception if v1 is not a valid index.
//    public void validate(int v1) {
//        if (v1 >= this.size || v1 < 0) {
//            throw new IllegalArgumentException();
//        }
//    }
//
//    // Returns the size of the set v1 belongs to.
//    public int sizeOf(int v1) {
//        validate(v1);
//        return -parent[find(v1)];
//    }
//
//    // Returns the parent of v1. If v1 is the root of a tree, returns the negative size of the tree for which v1 is the root.
//    public int parent(int v1) {
//        return parent[v1];
//    }
//
//    // Returns true if nodes v1 and v2 are connected.
//    public boolean connected(int v1, int v2) {
//        validate(v1);
//        validate(v2);
//        return find(v1) == find(v2);
//    }
//
//    // Connects two elements v1 and v2 together. v1 and v2 can be any valid elements, and a union-by-size heuristic is used. If the sizes of the sets are equal, tie break by connecting v1’s root to v2’s root. Unioning a vertex with itself or vertices that are already connected should not change the sets, but it may alter the internal structure of the data structure.
//    public void union(int v1, int v2) {
//        validate(v1);
//        validate(v2);
//        int root1 = find(v1);
//        int root2 = find(v2);
//        if (root1 == root2) {
//            return;
//        }
//        if (parent(root1) < parent(root2)) {
//            parent[root1] = parent[root1] + parent[root2];
//            parent[root2] = root1;
//        } else {
//            parent[root2] = parent[root2] + parent[root1];
//            parent[root1] = root2;
//        }
//    }
//
//    // Returns the root of the set v1 belongs to. Path-compression is employed allowing for fast search-time.
//    public int find(int v1) {
//        if (parent(v1) < 0) {
//            return v1;
//        }
//        int root = find(parent(v1));
//        parent[v1] = root;
//        return root;
//    }
//}
//

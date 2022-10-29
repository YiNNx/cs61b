public class BubbleGrid {
    /* Example
    Given the grid
    [[1, 1, 0],
     [1, 0, 0],
     [1, 1, 0],
     [1, 1, 1]]
    and darts [[2, 2], [2, 0]]
    popBubbles() should return [0, 4]
     */
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
                    unionU(dot, dotB,grid);
                    unionU(dot, dotR,grid);
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
                if (!stuckT && ((isBubble(dotT, grid) && connectedU(dotStuck, dotT)) || dotT[0] == -1)) {
                    stuckT = true;
                }
            }
            if (stuckB || stuckR || stuckT || stuckL) {
                if (!stuckB)
                    res[i] += sizeofU(dotB, grid);
                if (!stuckL && !connectedU(dotB, dotL))
                    res[i] += sizeofU(dotL, grid);
                if (!stuckR && !connectedU(dotR, dotB) && !connectedU(dotR, dotL))
                    res[i] += sizeofU(dotR, grid);
                if (!stuckT && !connectedU(dotT, dotB) && !connectedU(dotT, dotL) && !connectedU(dotT, dotR))
                    res[i] += sizeofU(dotT, grid);
            }
            grid[darts[i][0]][darts[i][1]] = 1;
            unionU(darts[i], dotT,grid);
            unionU(darts[i], dotB,grid);
            unionU(darts[i], dotR,grid);
            unionU(darts[i], dotL,grid);
        }
        return res;
    }

    private static void unionU(int[] dot1, int[] dot2, int[][] grid) {
        if (!isBubble(dot1, grid) || !isBubble(dot2, grid)) return;
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
        if (!isBubble(dot, grid)) return 0;
        return u.sizeOf(dot[0] * col + dot[1]);
    }
}

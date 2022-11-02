package leetCode773;

import java.util.*;

//    input: board = [[1,2,3],
//                    [4,0,5]]
//    output: 1

//    input: board = [[1,2,3],
//                    [5,4,0]]
//    output: -1

public class Solution {
    private final int[][] NEIGHBORS = {{1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4}};
    private final Integer[] end={1,2,3,4,5,0};

    private Queue<Integer[]> searchQueue = new LinkedList<>();
    private Queue<Integer> timesQueue = new LinkedList<>();
    private Set<Integer> visited = new HashSet<>();

    private boolean visited(Integer[] n) {
        int code = 0;
        for (int item : n) code = item + code * 6;
        return visited.contains(code);
    }

    private boolean visit(Integer[] n) {
        int code = 0;
        for (int item : n) code = item + code * 6;
        return visited.add(code);
    }

    private Integer[][] getNeighbors(Integer[] n) {
        int zeroIndex = 0;
        for (int i = 0; i < n.length; i++) {
            if (n[i] == 0) {
                zeroIndex = i;
                break;
            }
        }
        Integer[][] res = new Integer[NEIGHBORS[zeroIndex].length][];
        for (int i = 0; i < NEIGHBORS[zeroIndex].length; i++) {
            Integer[] neighbor = swap(n, zeroIndex, NEIGHBORS[zeroIndex][i]);
            if(!visited(neighbor)) res[i]=neighbor;
        }
        return res;
    }

    private Integer[] swap(Integer[] board, int i1, int i2) {
        Integer[] b = board.clone();
        b[i1] = board[i2];
        b[i2] = board[i1];
        return b;
    }

    private int bfs(Integer[] start) {
        if(Arrays.equals(start,end)) return 0;
        searchQueue.add(start);
        timesQueue.add(0);
        visit(start);
        while (searchQueue.peek() != null) {
            Integer[] out = searchQueue.poll();
            Integer outTime = timesQueue.poll();
            Integer[][] neighbors = getNeighbors(out);
            for (Integer[] neighbor : neighbors) {
                if(neighbor!=null){
                    if(Arrays.equals(neighbor,end)) return outTime+1;
                    searchQueue.add(neighbor);
                    timesQueue.add(outTime + 1);
                    visit(neighbor);
                }
            }
        }
        return -1;
    }

    public int slidingPuzzle(int[][] board) {
        return bfs(combine(board));
    }

    private static Integer[] combine(int[][] board) {
        int[] res = new int[board.length * board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, res, i * board[i].length, board[i].length);
        }
        return Arrays.stream(res).boxed().toArray(Integer[]::new);
    }

    public static void main(String[] args) {
        int[][] test={{4,1,2},{5,0,3}};
        int res=new Solution().slidingPuzzle(test);
        System.out.println(res);
    }
}

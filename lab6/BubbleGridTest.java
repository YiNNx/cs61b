import java.util.Arrays;

public class BubbleGridTest {
    public static void main(String[] args) {
        int[][] grid = new int[5][1];
        grid[0] = new int[]{1};
        grid[1] = new int[]{1};
        grid[2] = new int[]{1};
        grid[3] = new int[]{1};
        grid[4] = new int[]{1};

        int[][] darts = new int[5][2];
        darts[0] = new int[]{3,0};
        darts[1] = new int[]{4,0};
        darts[2] = new int[]{1,0};
        darts[3] = new int[]{2,0};
        darts[4] = new int[]{0,0};
        System.out.println(Arrays.toString(BubbleGrid.popBubbles(grid, darts)));
    }
}

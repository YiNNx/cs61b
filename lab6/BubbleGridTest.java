import java.util.Arrays;

public class BubbleGridTest {
    public static void main(String[] args) {
        int[][] grid = new int[][] {
                {1,0,1},
                {1,1,1}};
        int[][] darts = new int[][]{{0,0},{0,2},{1,1}};
//        BubbleGridOptimized b=new BubbleGridOptimized();
        System.out.println(Arrays.toString(BubbleGrid.popBubbles(grid, darts)));
    }
}

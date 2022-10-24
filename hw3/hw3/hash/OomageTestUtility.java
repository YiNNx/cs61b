package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        int min = N / 50;
        int max = (int) (N / 2.5);
        int[] bucketsNum = new int[M];
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (++bucketsNum[bucketNum] >= max) {
                return false;
            }
        }
        for (int n : bucketsNum) {
            if (n < min) {
                return false;
            }
        }

        return true;
    }
}

public class Maximizer {
    public static <T> Comparator<T> max(Comparator<T>[] objects) {
        int maxIndex = 0;
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].compareTo((T)objects[maxIndex]) > 0) {
                maxIndex = i;
            }
        }

        return objects[maxIndex];
    }

}

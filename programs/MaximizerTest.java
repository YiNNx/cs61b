import java.util.HashSet;
import java.util.Set;

public class MaximizerTest {
    public static void main(String[] args) {
        Dog[] dogs = new Dog[3];
        dogs[0] = new Dog("a", 1);
        dogs[1] = new Dog("b", 3);
        dogs[2] = new Dog("c", 2);
        Dog maxDog = (Dog) Maximizer.max(dogs);
        System.out.print(maxDog.name);
        java.util.Comparator<Dog> nc=Dog.getNameComparator();
        nc.compare(dogs[1],dogs[2]);

        Set<String> set=new HashSet<>();

    }
}

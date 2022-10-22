public class Dog extends Object implements Animal, Comparator<Dog> {
    public String name;
    public int age;


    public int compareTo(Dog item) {
        return this.age - item.age;
    }

    public Dog() {
        name = "default";
        age = 0;
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private static class NameComparator implements java.util.Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }
    }

    public static NameComparator getNameComparator() {
        return new NameComparator();
    }

    public void run() {
        System.out.print("dog " + name + " run! ");
    }

    public void eat() {
        System.out.print("dog " + name + " eat! ");
    }
}

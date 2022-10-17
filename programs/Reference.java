public class Reference {
    String name;

    public Reference(String n) {
        this.name = n;
    }

    public Reference(Reference r) {
        this.name = r.name;
    }

    public static void equals() {
        Reference a = new Reference("a");
        System.out.println(a.name);
        Reference b = a;
        b.name = "b";
        System.out.println(a.name);
    }

    public static void cloneReference() {
        Reference a = new Reference("a");
        System.out.println(a.name);
        Reference b = new Reference(a);
        b.name = "b";
        System.out.println(a.name);
    }

    public static void main(String[] args) {
        equals();
        cloneReference();
    }
}
public class HelloWorld {
    String name;

    public HelloWorld(String n) {
        name = n;
    }

    public static void main(String[] args) {
        Reference h = new Reference("me");
        System.out.println("hello,world!" + h.name);
    }
}
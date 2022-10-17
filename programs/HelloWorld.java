public class HelloWorld {
    String name;

    public HelloWorld(String n) {
        name = n;
    }

    public static void main(String[] args) {
        HelloWorld h = new HelloWorld("me");
        System.out.println("hello,world!" + h.name);
    }
}
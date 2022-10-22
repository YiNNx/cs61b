public class HelloWorld {
    String name;

    public HelloWorld(String n) {
        name = n;
    }

    public static void main(String[] args) {
        Reference h = new Reference("me");
        System.out.println("hello,world!" + h.name);
        int a=-5;
        double b=-1082882828282880.91;
        System.out.println((double)a);
        System.out.println((long)a);
        System.out.println((char)a);
        System.out.println((int)b);
        System.out.println((long)b);
        System.out.println(b);


    }
}
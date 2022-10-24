public class HelloWorld {
    String name;

    public HelloWorld(String n) {
        name = n;
    }

    public static void main(String[] args) {
        Reference h = new Reference("me");
        char[] str="hello".toCharArray();
        for(int i=0;i< str.length;i++){
            System.out.println(str[i]);
        }
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
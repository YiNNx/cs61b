public class UnionFindTest {
    public static void main(String[] args) {
        UnionFind u=new UnionFind(1000);
        u.union(0,3);
        u.union(1,3);
        u.union(2,4);
        u.union(0,4);
        System.out.println(u.connected(0,2));
        System.out.println(u.connected(0,2));
    }
}

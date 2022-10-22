public class Labrador extends Dog{
    public int weight;
    public Labrador(String name){
        super(name,0);
        weight=10;
    }

    public Labrador(){
        super();
        weight=10;
    }

    public void printName(){
        System.out.print(name);
    }

    @Override
    public void run(){
        System.out.print("labrador runs faster!");
    }

    public static void main(String[] args){
        Animal berton = new Labrador();
        berton.eat();
        Dog a=new Labrador("a");
        Labrador b=(Labrador) a;
        b.printName();
    }
}


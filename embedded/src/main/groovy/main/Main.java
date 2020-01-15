package main;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 ) {
            System.out.println("HELLO WORLD");
            System.out.println(args[1]);
        }
        else {
            System.out.println("You're missing some args OR too much args !");
        }
    }
}

package main;

import dsl.GroovyDSL;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 ) {
            new GroovyDSL().eval(new File(args[0]));
        }
        else {
            System.out.println("You're missing some args OR too much args !");
            new GroovyDSL().eval(new File("scripts/script1.groovy"));
        }
    }
}

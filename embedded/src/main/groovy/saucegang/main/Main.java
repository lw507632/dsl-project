package saucegang.main;

import saucegang.dsl.SauceGangDSL;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main::main::IN");

        SauceGangDSL dsl = new SauceGangDSL();
        if (args.length == 1) {
            System.out.println("Script name : " + args[0]);
            System.out.println("Running.. : " + args[0]);
            File scriptFile = new File(args[0]);
            System.out.println(scriptFile.getAbsolutePath());
            dsl.eval(scriptFile);
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute");
        }
        System.out.println("Main::main::OUT");
    }
}

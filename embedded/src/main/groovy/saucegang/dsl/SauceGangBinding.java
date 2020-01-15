package saucegang.dsl;

import groovy.lang.Binding;
import groovy.lang.Script;

public class SauceGangBinding extends Binding {
    // the groovy script using this binding
    private Script script;

    public SauceGangBinding(){
        super();
    }
}

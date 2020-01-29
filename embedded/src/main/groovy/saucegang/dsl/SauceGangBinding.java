package saucegang.dsl;

import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.Script;

public class SauceGangBinding extends Binding {
    private SauceGangModel model;
    private Script script;

    public SauceGangBinding() {
        super();
    }

    @SuppressWarnings("rawtypes")
    public SauceGangBinding(Map variables) {
        super(variables);
    }

    public SauceGangBinding(Script script) {
        super();
        this.script = script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public void setSauceGangModel(SauceGangModel model) {
        this.model = model;
    }

    public Object getVariable(String name) {
        // Easter egg (to show you this trick: seb is now a keyword!)
        if ("seb".equals(name)) {
            // could do something else like: ((App) this.getVariable("app")).action();
            System.out.println("Seb, c'est bien");
            return script;
        }
        return super.getVariable(name);
    }

    public void setVariable(String name, Object value) {
        super.setVariable(name, value);
    }

    public SauceGangModel getSauceGangModel() {
        return this.model;
    }
}
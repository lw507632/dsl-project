package dsl

public class VariablesBinding extends Binding {
    private GroovyModel model;
    private Script script;

    public VariablesBinding() { super(); }

    @SuppressWarnings("rawtypes")
    public VariablesBinding(Map variables) { super(variables); }

    public VariablesBinding(Script script) {
        super();
        this.script = script;
    }

    public void setScript(Script script) { this.script = script; }


    public void setGroovuinoMLModel(GroovyModel model) { this.model = model; }

    public Object getVariable(String name) { return super.getVariable(name); }

    public void setVariable(String name, Object value) { super.setVariable(name, value); }

    // we don't need it, Groovy actually generates getters for declared fields
    public GroovyModel getGroovyModel () { return this.model; }

}

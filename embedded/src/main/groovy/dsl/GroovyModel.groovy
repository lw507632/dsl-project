package dsl

import io.github.mosser.arduinoml.kernel.App
import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.behavioral.Transition
import io.github.mosser.arduinoml.kernel.generator.ToWiring
import io.github.mosser.arduinoml.kernel.generator.Visitor
import io.github.mosser.arduinoml.kernel.structural.Brick
import io.github.mosser.arduinoml.kernel.structural.SIGNAL
import io.github.mosser.arduinoml.kernel.structural.Sensor
import io.github.mosser.arduinoml.kernel.structural.Actuator

public class GroovyModel {

    // fields
    private List<Brick> brickList;
    private List<State> stateList;
    private State initialState;
    private Binding binding;

    public GroovyModel(Binding binding) {
        this.brickList = new ArrayList<>();
        this.stateList = new ArrayList<>();
        this.binding = binding;
    }

    public createSensor(String sensorName, Integer pinNumber) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorName);
        sensor.setPin(pinNumber);
        this.brickList.add(sensor);
        this.binding.setVariable(sensorName, sensor);
    }

    public void createActuator(String name, Integer pinNumber) {
        Actuator actuator = new Actuator();
        actuator.setName(name);
        actuator.setPin(pinNumber);
        this.brickList.add(actuator);
        this.binding.setVariable(name, actuator);
    }

    public void createState(String name, List<Action> actions) {
        State state = new State();
        state.setName(name);
        state.setActions(actions);
        this.stateList.add(state);
        this.binding.setVariable(name, state);
    }

    public void createTransition(State from, State to, Sensor sensor, SIGNAL value) {
        Transition transition = new Transition();
        transition.setNext(to);
        transition.setSensor(sensor);
        transition.setValue(value);
        from.setTransition(transition);
    }


    // don't need to create setters for fields, Groovy does it !
    public void setInitialState(State state) {
        this.initialState = state;
    }


    @SuppressWarnings("rawtypes")
    public Object generateCode(String appName) {
        App app = new App();
        app.setName(appName);
        app.setBricks(this.bricks);
        app.setStates(this.states);
        app.setInitial(this.initialState);
        Visitor codeGenerator = new ToWiring();
        app.accept(codeGenerator);

        return codeGenerator.getResult();
    }


}

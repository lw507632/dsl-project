package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Operator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.ArrayList;
import java.util.List;

public class Condition implements Visitable {
    private List<Sensor> sensors;
    private List<Operator> operators;
    private List<SIGNAL> values;

    public Condition() {
        this.sensors = new ArrayList<>();
        this.operators = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public List<Operator> getOperators() {
        return operators;
    }


    public List<SIGNAL> getValues() {
        return values;
    }

    public Condition addValue(SIGNAL signal){
        values.add(signal);
        return this;
    }

    public Condition addOperator(Operator operator){
        operators.add(operator);
        return this;
    }

    public Condition addSensor(Sensor sens){
        sensors.add(sens);
        return this;
    }

    public Condition addSensor(Sensor sens, SIGNAL signal){
        sensors.add(sens);
        values.add(signal);
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

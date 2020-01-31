package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.ArrayList;
import java.util.List;

public class Transition implements Visitable {

	private State next;
	private List<Sensor> sensors;
	private List<SIGNAL> values;
	private Condition condition;


	public Transition(){
		sensors = new ArrayList<>();
		values = new ArrayList<>();
	}

	public State getNext() {
		return next;
	}

	public Transition setNext(State next) {
		this.next = next; return this;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void addSensor(Sensor sensor){
		sensors.add(sensor);
	}

	public void addValue(SIGNAL signal){
		values.add(signal);
	}

	public List<SIGNAL> getValues() {
		return values;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public Transition setCondition(Condition cond) {
		this.condition = cond;
		return this;
	}
	public Condition getCondition() {
		return condition;
	}
}

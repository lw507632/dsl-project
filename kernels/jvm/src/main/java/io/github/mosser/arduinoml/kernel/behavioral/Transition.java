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
	private List<Condition> conditions;


	public Transition(){
		sensors = new ArrayList<>();
		values = new ArrayList<>();
		conditions = new ArrayList<>();
	}

	public State getNext() {
		return next;
	}

	public void setNext(State next) {
		this.next = next;
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

	public List<Condition> getConditions(){
		return conditions;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void addCondition(Condition cond) {
		conditions.add(cond);
	}
}

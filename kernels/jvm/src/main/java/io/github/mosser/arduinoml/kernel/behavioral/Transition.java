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


	public Transition(){
		sensors = new ArrayList<>();
		values = new ArrayList<>();
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

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

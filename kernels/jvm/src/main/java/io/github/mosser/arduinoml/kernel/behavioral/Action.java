package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;

public class Action implements Visitable {

	private String value;
	private Actuator actuator;

	public Action(Actuator actuator,String value) {
		this.value = value;
		this.actuator = actuator;
	}

	public Action(Actuator actuator,SIGNAL value) {
		this.value = value.toString();
		this.actuator = actuator;
	}

	public Action() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(SIGNAL signal){
		this.value = signal.toString();
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Actuator getActuator() {
		return actuator;
	}

	public void setActuator(Actuator actuator) {
		this.actuator = actuator;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

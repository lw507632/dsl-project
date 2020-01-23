package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Actuator extends Brick {

	public Actuator(String name, int pin) {
		super(name, pin);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

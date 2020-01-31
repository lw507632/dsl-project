package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Actuator extends Brick {

	public Actuator(String name, int pin) {
		super(name, pin);
	}

	public Actuator(String name, int pin, BrickType type) {
		super(name, pin, type);
	}

	public Actuator() {
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

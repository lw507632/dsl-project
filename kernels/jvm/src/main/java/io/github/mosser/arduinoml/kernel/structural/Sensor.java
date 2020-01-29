package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Sensor extends Brick {
	SensorType type;

	public Sensor(String name, int pin) {
		super(name, pin);
		type = SensorType.DIGITAL;
	}

	public Sensor(String name, int pin, SensorType type) {
		super(name, pin);
		this.type = type;
	}

	public Sensor() {
		type = SensorType.DIGITAL;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public SensorType getType() {
		return type;
	}
}

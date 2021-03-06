package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.NamedElement;
import io.github.mosser.arduinoml.kernel.generator.Visitable;

public abstract class Brick implements NamedElement, Visitable {

	private String name;
	private int pin;
	BrickType type = BrickType.DIGITAL;;

	public Brick(String name, int pin) {
		this.name = name;
		this.pin = pin;
	}

	public Brick(String name,int pin, BrickType type) {
		this.name = name;
		this.pin = pin;
		this.type = type;
	}

	public Brick() {
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public BrickType getType() {
		return type;
	}

	public void setType(BrickType type) {
		this.type = type;
	}
}
package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.Action;
import io.github.mosser.arduinoml.kernel.behavioral.State;
import io.github.mosser.arduinoml.kernel.behavioral.Transition;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.Arrays;

public class Scenario_4 {

	public static void main(String[] args) {

		// Declaring elementary bricks
		Sensor button = new Sensor();
		button.setName("BUTTON");
		button.setPin(9);

		Actuator led = new Actuator();
		led.setName("LED");
		led.setPin(12);

		Actuator buzzer = new Actuator();
		buzzer.setName("BUZZER");
		buzzer.setPin(11);

		// Declaring states
		State everythingOff = new State();
		everythingOff.setName("everythingOff");

		State BuzzerOffLedOn = new State();
		BuzzerOffLedOn.setName("BuzzerOffLedOn");

		State BuzzerOnLedOff = new State();
		BuzzerOnLedOff.setName("BuzzerOnLedOff");

		// Creating actions
		Action switchTheLightOn = new Action();
		switchTheLightOn.setActuator(led);
		switchTheLightOn.setValue(SIGNAL.HIGH);

		Action switchTheLightOff = new Action();
		switchTheLightOff.setActuator(led);
		switchTheLightOff.setValue(SIGNAL.LOW);

		// Binding actions to states
		on.setActions(Arrays.asList(switchTheLightOn));
		off.setActions(Arrays.asList(switchTheLightOff));

		// Creating transitions
		Transition on2off = new Transition();
		on2off.setNext(off);
		on2off.setSensor(button);
		on2off.setValue(SIGNAL.HIGH);

		Transition off2on = new Transition();
		off2on.setNext(on);
		off2on.setSensor(button);
		off2on.setValue(SIGNAL.HIGH);

		// Binding transitions to states
		on.setTransition(on2off);
		off.setTransition(off2on);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Switch!");
		theSwitch.setBricks(Arrays.asList(button,led,buzzer));
		theSwitch.setStates(Arrays.asList(everythingOff, BuzzerOffLedOn, BuzzerOnLedOff));
		theSwitch.setInitial(everythingOff);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

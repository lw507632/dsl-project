package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;

public class Scenario_3 {

	public static void main(String[] args) {

		// Declaring elementary bricks
		Sensor button = new Sensor("button", 9, BrickType.DIGITAL);
		Actuator led = new Actuator("LED",12);

		// Declaring states
		State on = new State("on");
		State off = new State("off");

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

		SimpleCondition simpleConditionButtonOn = new SimpleCondition(Comparator.EQUALS, button, "HIGH");
		MultipleCondition multipleCondition = new MultipleCondition();
		multipleCondition.addCondition(simpleConditionButtonOn);


		Transition on2off = new Transition();
		on2off.setNext(on);
		on2off.setMultipleCondition(multipleCondition);


		Transition off2on = new Transition();
		off2on.setNext(on);
		off2on.setMultipleCondition(multipleCondition);


		// Binding transitions to states
		on.setTransition(on2off);
		off.setTransition(off2on);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Scenario 3!");
		theSwitch.setBricks(Arrays.asList(button, led ));
		theSwitch.setStates(Arrays.asList(on, off));
		theSwitch.setInitial(off);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

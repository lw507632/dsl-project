package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;

public class Scenario_1 {

	public static void main(String[] args) {

		// Declaring elementary bricks
		Sensor button = new Sensor();
		button.setName("button");
		button.setPin(12);

		Actuator led = new Actuator();
		led.setName("LED");
		led.setPin(9);

		Actuator buzzer = new Actuator();
		buzzer.setName("BUZZER");
		buzzer.setPin(8);

		// Declaring states
		State button_released = new State();
		button_released.setName("button_released");

		State button_pushed = new State();
		button_pushed.setName("button_pushed");

		// Creating actions
		Action switchTheLightOn = new Action();
		switchTheLightOn.setActuator(led);
		switchTheLightOn.setValue(SIGNAL.HIGH);

		Action switchTheLightOff = new Action();
		switchTheLightOff.setActuator(led);
		switchTheLightOff.setValue(SIGNAL.LOW);

		Action switchTheBuzzerOn = new Action();
		switchTheBuzzerOn.setActuator(buzzer);
		switchTheBuzzerOn.setValue(SIGNAL.HIGH);

		Action switchTheBuzzerOff = new Action();
		switchTheBuzzerOff.setActuator(buzzer);
		switchTheBuzzerOff.setValue(SIGNAL.LOW);

		// Binding actions to states
		button_released.setActions(Arrays.asList(switchTheLightOff, switchTheBuzzerOff));
		button_pushed.setActions(Arrays.asList(switchTheBuzzerOn, switchTheLightOn));

		// Creating transitions
		Transition released2pushed = new Transition();
		released2pushed.setNext(button_pushed);
		SimpleCondition simpleButtonPushedCondition = new SimpleCondition(Comparator.EQUALS, button, "HIGH");
		released2pushed.setCondition(simpleButtonPushedCondition);


		Transition pushed2released = new Transition();
		pushed2released.setNext(button_released);
		SimpleCondition simpleButtonReleasedCondition = new SimpleCondition(Comparator.EQUALS, button, "LOW");
		pushed2released.setCondition(simpleButtonReleasedCondition);


		// Binding transitions to states
		button_released.setTransition(released2pushed);
		button_pushed.setTransition(pushed2released);

		// Building the App
		App theSwitch = new App();
		theSwitch.setName("Scenario 1!");
		theSwitch.setBricks(Arrays.asList(button, led, buzzer));
		theSwitch.setStates(Arrays.asList(button_pushed, button_released));
		theSwitch.setInitial(button_released);

		// Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);

		// Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

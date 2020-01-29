package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;

public class DoubleButtonTriggerLed {

	public static void main(String[] args) {
		// Declaring elementary bricks
		Sensor button1 = new Sensor("BUTTON1", 9);
		Sensor button2 = new Sensor("BUTTON2",10);
		Actuator buzzer = new Actuator("LED", 12);

		// Declaring states
		State buttons_released = new State("buttons_released");
		State buttons_pushed = new State("buttons_pushed");

		Action switchTheBuzzerOn = new Action(buzzer,SIGNAL.HIGH);
		Action switchTheBuzzerOff = new Action(buzzer,SIGNAL.LOW);      // Binding actions to states

		buttons_released.setActions(Arrays.asList(switchTheBuzzerOff));
		buttons_pushed.setActions(Arrays.asList(switchTheBuzzerOn));      // Creating transitions

		Transition released2pushed = new Transition();
		released2pushed.setNext(buttons_pushed);

		Transition pushed2released = new Transition();
		pushed2released.setNext(buttons_released);

		Condition b1_and_b2 = new Condition();
		b1_and_b2.addSensor(button1,SIGNAL.HIGH).addOperator(Operator.AND).addSensor(button2,SIGNAL.HIGH);
		released2pushed.setCondition(b1_and_b2);

		Condition b1_or_b2 = new Condition();
		b1_or_b2.addSensor(button1,SIGNAL.LOW).addOperator(Operator.OR).addSensor(button2,SIGNAL.LOW);
		pushed2released.setCondition(b1_or_b2);

		buttons_released.setTransition(released2pushed);
		buttons_pushed.setTransition(pushed2released);      // Building the App


		App theSwitch = new App();
		theSwitch.setName("DoubleButtonTriggerLed!");
		theSwitch.setBricks(Arrays.asList(button1, button2, buzzer));
		theSwitch.setStates(Arrays.asList(buttons_pushed, buttons_released));
		theSwitch.setInitial(buttons_released);      // Generating Code
		Visitor codeGenerator = new ToWiring();
		theSwitch.accept(codeGenerator);      // Printing the generated code on the console
		System.out.println(codeGenerator.getResult());
	}

}

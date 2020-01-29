package io.github.mosser.arduinoml.kernel.samples;


import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;

public class AnalogInputScenario {

    public static void main(String args[]) {
        // Declaring elementary bricks
        Sensor lightSensor = new Sensor("LS", 12, SensorType.ANALOGICAL);
        Sensor button = new Sensor("button", 11);
        Actuator led = new Actuator("LED", 9);

        // Declaring states
        State initial = new State("initial");
        State led_on = new State("led_on");

        // Action
        Action switchTheLedOn = new Action(led, SIGNAL.HIGH);
        Action switchTheLedOff = new Action(led, SIGNAL.LOW);

        led_on.setActions(Arrays.asList(switchTheLedOn));
        initial.setActions(Arrays.asList(switchTheLedOff));

        //Condition
        MultipleCondition multipleCondition = new MultipleCondition();
        multipleCondition.addCondition(new SimpleCondition(Comparator.SUPERIOR, lightSensor, "200"));
        multipleCondition.addOperator(Operator.OR);
        multipleCondition.addCondition(new SimpleCondition(Comparator.EQUALS, button, "HIGH"));

        //Transition
        Transition initial2led = new Transition();

        initial2led.setNext(led_on).setMultipleCondition(multipleCondition);

        initial.setTransition(initial2led);

        App theSwitch = new App();
        theSwitch.setName("Scenario 4");
        theSwitch.setBricks(Arrays.asList(led, lightSensor, button));
        theSwitch.setStates(Arrays.asList(initial, led_on));
        theSwitch.setInitial(initial);      // Generating Code


        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);      // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }
}

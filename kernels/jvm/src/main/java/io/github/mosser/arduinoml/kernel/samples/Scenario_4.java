package io.github.mosser.arduinoml.kernel.samples;


import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;

import java.util.Arrays;

public class Scenario_4 {
    public static void main(String[] args) {
        // Declaring elementary bricks
        Sensor button1 = new Sensor("BUTTON1", 12);
        Actuator buzzer = new Actuator("BUZZER", 9);
        Actuator led = new Actuator("LED", 8);

        // Declaring states
        State initial = new State("initial");
        State led_on = new State("led_on");
        State buzzer_on = new State("buzzer_on");

        // Action
        Action switchTheBuzzerOn = new Action(buzzer,SIGNAL.HIGH);
        Action switchTheLedOn = new Action(led,SIGNAL.HIGH);

        Action switchTheBuzzerOff = new Action(buzzer,SIGNAL.LOW);
        Action switchTheLedOff = new Action(led,SIGNAL.LOW);


        buzzer_on.setActions(Arrays.asList(switchTheBuzzerOn));
        led_on.setActions(Arrays.asList(switchTheBuzzerOff, switchTheLedOn));
        initial.setActions(Arrays.asList(switchTheLedOff));


        SimpleCondition simpleConditionButtonOn = new SimpleCondition(Comparator.EQUALS, button1, "HIGH");


        // Transitions
        Transition initial2buzz = new Transition();
        Transition buzz2led = new Transition();
        Transition led2initial = new Transition();

        initial2buzz.setNext(buzzer_on).setCondition(simpleConditionButtonOn);
        buzz2led.setNext(led_on).setCondition(simpleConditionButtonOn);
        led2initial.setNext(initial).setCondition(simpleConditionButtonOn);

        initial.setTransition(initial2buzz);
        buzzer_on.setTransition(buzz2led);
        led_on.setTransition(led2initial);

        App theSwitch = new App();
        theSwitch.setName("Scenario 4!");
        theSwitch.setBricks(Arrays.asList(button1,led,buzzer));
        theSwitch.setStates(Arrays.asList(initial, led_on, buzzer_on));
        theSwitch.setInitial(initial);      // Generating Code


        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);      // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());


    }
}

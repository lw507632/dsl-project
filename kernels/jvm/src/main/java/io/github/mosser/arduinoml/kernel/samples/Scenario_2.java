package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Actuator;
import io.github.mosser.arduinoml.kernel.structural.Operator;
import io.github.mosser.arduinoml.kernel.structural.SIGNAL;
import io.github.mosser.arduinoml.kernel.structural.Sensor;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.Arrays;

@Ignore
public class Scenario_2 {

    public static void main(String[] args) {

        // Declaring elementary bricks

        Sensor button1 = new Sensor();
        button1.setName("BUTTON1");
        button1.setPin(9);

        Sensor button2 = new Sensor();
        button2.setName("BUTTON2");
        button2.setPin(10);

        Actuator buzzer = new Actuator();
        buzzer.setName("LED");
        buzzer.setPin(12);

        // Declaring states

        State buttons_released = new State();
        buttons_released.setName("buttons_released");

        State buttons_pushed = new State();
        buttons_pushed.setName("buttons_pushed");

        Action switchTheBuzzerOn = new Action();
        switchTheBuzzerOn.setActuator(buzzer);
        switchTheBuzzerOn.setValue(SIGNAL.HIGH);

        Action switchTheBuzzerOff = new Action();
        switchTheBuzzerOff.setActuator(buzzer);
        switchTheBuzzerOff.setValue(SIGNAL.LOW);

        // Binding actions to states
        buttons_released.setActions(Arrays.asList(switchTheBuzzerOff));
        buttons_pushed.setActions(Arrays.asList(switchTheBuzzerOn));


        // Creating transitions
        Transition released2pushed = new Transition();
        released2pushed.setNext(buttons_pushed);
        SimpleCondition simpleButton1PushedCondition = new SimpleCondition(Comparator.EQUALS, button1, "HIGH");
        SimpleCondition simpleButton2PushedCondition = new SimpleCondition(Comparator.EQUALS, button2, "HIGH");
        MultipleCondition multipleCondition = new MultipleCondition();
        multipleCondition.addCondition(simpleButton1PushedCondition);
        multipleCondition.addCondition(simpleButton2PushedCondition);
        multipleCondition.addOperator(Operator.AND);
        released2pushed.setMultipleCondition(multipleCondition);


        Transition pushed2released = new Transition();
        pushed2released.setNext(buttons_released);
        SimpleCondition simpleButton1ReleasedCondition = new SimpleCondition(Comparator.EQUALS, button1, "LOW");
        SimpleCondition simpleButton2ReleasedCondition = new SimpleCondition(Comparator.EQUALS, button2, "LOW");
        MultipleCondition multipleConditionReleased = new MultipleCondition();
        multipleConditionReleased.addCondition(simpleButton1ReleasedCondition);
        multipleConditionReleased.addCondition(simpleButton2ReleasedCondition);
        multipleConditionReleased.addOperator(Operator.OR);
        pushed2released.setMultipleCondition(multipleConditionReleased);

        buttons_released.setTransition(released2pushed);
        buttons_pushed.setTransition(pushed2released);      // Building the App


        App theSwitch = new App();
        theSwitch.setName("Scenario 2!");
        theSwitch.setBricks(Arrays.asList(button1, button2, buzzer));
        theSwitch.setStates(Arrays.asList(buttons_pushed, buttons_released));
        theSwitch.setInitial(buttons_released);      // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);      // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }
}

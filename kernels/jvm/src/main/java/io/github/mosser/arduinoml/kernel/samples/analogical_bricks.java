package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;

public class analogical_bricks {
    public static void main(String[] args) {

        // Declaring elementary bricks
        Sensor tempSensor = new Sensor("TEMPSENSOR",1, BrickType.ANALOGICAL);
        Actuator led = new Actuator("LED",9, BrickType.ANALOGICAL);

        // Declaring states
        State no_fire = new State();
        no_fire.setName("no_fire");

        State fire = new State();
        fire.setName("fire");

        // Creating actions
        Action switchTheAlarmOn = new Action();
        switchTheAlarmOn.setActuator(led);
        switchTheAlarmOn.setValue("50");

        Action switchTheAlarmOff = new Action();
        switchTheAlarmOff.setActuator(led);
        switchTheAlarmOff.setValue("0");

        // Binding actions to states
        fire.setActions(Arrays.asList(switchTheAlarmOn));
        no_fire.setActions(Arrays.asList(switchTheAlarmOff));

        // Creating transitions
        Transition nofire2fire = new Transition();
        nofire2fire.setNext(fire);
        SimpleCondition simpleFireCondition = new SimpleCondition(Comparator.SUPERIOR, tempSensor, "22");
        nofire2fire.setCondition(simpleFireCondition);


        Transition fire2nofire = new Transition();
        fire2nofire.setNext(no_fire);
        SimpleCondition simpleNoFireCondition1 = new SimpleCondition(Comparator.INFERIOR, tempSensor, "57");
        MultipleCondition multipleCondition2 = new MultipleCondition();
        multipleCondition2.addCondition(simpleNoFireCondition1);
        fire2nofire.setCondition(multipleCondition2);


        // Binding transitions to states
        fire.setTransition(fire2nofire);
        no_fire.setTransition(nofire2fire);

        // Building the App
        App theSwitch = new App();
        theSwitch.setName("Scenario 1!");
        theSwitch.setBricks(Arrays.asList(tempSensor, led));
        theSwitch.setStates(Arrays.asList(fire, no_fire));
        theSwitch.setInitial(no_fire);

        // Generating Code
        Visitor codeGenerator = new ToWiring();
        theSwitch.accept(codeGenerator);

        // Printing the generated code on the console
        System.out.println(codeGenerator.getResult());
    }
}

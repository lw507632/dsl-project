package saucegang.dsl

import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.behavioral.Comparator
import io.github.mosser.arduinoml.kernel.behavioral.Condition
import io.github.mosser.arduinoml.kernel.behavioral.MultipleCondition
import io.github.mosser.arduinoml.kernel.behavioral.SimpleCondition
import io.github.mosser.arduinoml.kernel.structural.Actuator
import io.github.mosser.arduinoml.kernel.structural.BrickType
import io.github.mosser.arduinoml.kernel.structural.Operator
import io.github.mosser.arduinoml.kernel.structural.SIGNAL
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.structural.Sensor

abstract class SauceGangBaseScript extends Script {
    // sensor "name" pin n
    def sensor(String name) {
        [pin  : { n -> ((SauceGangBinding) this.getBinding()).getSauceGangModel().createSensor(name, n) },
         onPin: { n -> ((SauceGangBinding) this.getBinding()).getSauceGangModel().createSensor(name, n) }]
    }
    // actuator "name" pin n
    def actuator(String name) {
        [pin: { n -> ((SauceGangBinding) this.getBinding()).getSauceGangModel().createActuator(name, n) }]
    }
    // state "name" means actuator becomes signal [and actuator becomes signal]*n
    def state(String name) {
        List<Action> actions = new ArrayList<Action>()
        ((SauceGangBinding) this.getBinding()).getSauceGangModel().createState(name, actions)
        // recursive closure to allow multiple and statements
        def closure
        closure = { actuator ->
            [becomes: { signal ->
                Action action = new Action()
                action.setActuator(actuator instanceof String ? (Actuator) ((SauceGangBinding) this.getBinding()).getVariable(actuator) : (Actuator) actuator)
                action.setValue(signal instanceof String ? (SIGNAL) ((SauceGangBinding) this.getBinding()).getVariable(signal) : (SIGNAL) signal)
                actions.add(action)
                [and: closure]
            }]
        }
        [means: closure]
    }
    // initial state
    def initial(state) {
        ((SauceGangBinding) this.getBinding()).getSauceGangModel().setInitialState(state instanceof String ? (State) ((SauceGangBinding) this.getBinding()).getVariable(state) : (State) state)
    }

    def from(state1) {
        MultipleCondition multipleCondition = new MultipleCondition();

        def closure
        closure = { sensor ->
            [becomes: { signal ->
                multipleCondition.addCondition(createCondition(sensor instanceof String ? (Sensor) ((SauceGangBinding) this.getBinding()).getVariable(sensor) : (Sensor) sensor,
                        (String) signal))
                if(multipleCondition.getConditionList().size() > 1){
                    multipleCondition.addOperator(Operator.AND)
                }
                [and:  closure]

            }]
        }
        [to: { state2 ->
            ((SauceGangBinding) this.getBinding()).getSauceGangModel().createTransition(
                    state1 instanceof String ? (State) ((SauceGangBinding) this.getBinding()).getVariable(state1) : (State) state1,
                    state2 instanceof String ? (State) ((SauceGangBinding) this.getBinding()).getVariable(state2) : (State) state2,
                    multipleCondition);
            [when: closure]
        }]
    };

    // export name
    def export(String name) {
        println(((SauceGangBinding) this.getBinding()).getSauceGangModel().generateCode(name).toString())
    }
    // disable run method while running
    int count = 0

    abstract void scriptBody()

    def run() {
        if (count == 0) {
            count++
            scriptBody()
        } else {
            println "Run method is disabled"
        }
    }

    def getComparator(String comparator){
        if (comparator == ">"){
            println "getComparator >"
            return Comparator.SUPERIOR
        } else if (comparator == "<") {
            println "getComparator <"
            return Comparator.INFERIOR
        } else if (comparator == "<=") {
            println "getComparator <="
            return Comparator.INFERIOR_OR_EQUALS
        } else if (comparator == ">=") {
            println "getComparator >="
            return Comparator.SUPERIOR_OR_EQUALS
        } else if (comparator == "==") {
            println "getComparator =="
            return Comparator.EQUALS
        } else if (comparator == "!=") {
            println "getComparator !="
            return Comparator.NEQUALS
        } else {
            throw new Exception("getComparator : comparator unknown")
        }
    }


    def createCondition (Sensor sensor, String entry) {
        println sensor
        println entry
        entry = entry.toLowerCase()
        if (entry.equals("high") || entry.equals("low")){
            println "if high or low"
            return new SimpleCondition(Comparator.EQUALS, sensor, entry)
        } else {
            sensor.setType(BrickType.ANALOGICAL)
            println "else..?"
            entries = entry.split(" ")
            SimpleCondition simpleCondition = new SimpleCondition(getComparator(entries[0]), sensor, entries[1]);
            return simpleCondition
        }
    }

    def createBigCondition() {

    }
}
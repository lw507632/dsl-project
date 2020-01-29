package dsl

import io.github.mosser.arduinoml.kernel.behavioral.Action
import io.github.mosser.arduinoml.kernel.structural.Actuator
import io.github.mosser.arduinoml.kernel.structural.SIGNAL
import io.github.mosser.arduinoml.kernel.behavioral.State
import io.github.mosser.arduinoml.kernel.structural.Sensor


abstract class BaseScript extends Script {
    // sensor "name" pin n
    def sensor(String name) {
        [pin: { n -> ((VariablesBinding)this.getBinding()).getGroovyModel().createSensor(name, n) },
         onPin: { n -> ((VariablesBinding)this.getBinding()).getGroovyModel().createSensor(name, n)}]
    }

    // actuator "name" pin n
    def actuator(String name) {
        [pin: { n -> ((VariablesBinding)this.getBinding()).getGroovyModel().createActuator(name, n) }]
    }

    // state "name" means actuator becomes signal [and actuator becomes signal]*n
    def state(String name) {
        List<Action> actions = new ArrayList<Action>()
        ((VariablesBinding) this.getBinding()).getGroovyModel().createState(name, actions)
        // recursive closure to allow multiple and statements
        def closure
        closure = { actuator ->
            [becomes: { signal ->
                Action action = new Action()
                action.setActuator(actuator instanceof String ? (Actuator)((VariablesBinding)this.getBinding()).getVariable(actuator) : (Actuator)actuator)
                action.setValue(signal instanceof String ? (SIGNAL)((VariablesBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal)
                actions.add(action)
                [and: closure]
            }]
        }
        [means: closure]
    }

    // initial state
    def initial(state) {
        ((VariablesBinding) this.getBinding()).getGroovyModel().setInitialState(state instanceof String ? (State)((VariablesBinding)this.getBinding()).getVariable(state) : (State)state)
    }


    // from state1 to state2 when sensor becomes signal
    def from(state1) {
        [to: { state2 ->
            [when: { sensor ->
                [becomes: { signal ->
                    ((VariablesBinding) this.getBinding()).getGroovyModel().createTransition(
                            state1 instanceof String ? (State)((VariablesBinding)this.getBinding()).getVariable(state1) : (State)state1,
                            state2 instanceof String ? (State)((VariablesBinding)this.getBinding()).getVariable(state2) : (State)state2,
                            sensor instanceof String ? (Sensor)((VariablesBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor,
                            signal instanceof String ? (SIGNAL)((VariablesBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal)
                }]
            }]
        }]
    }

    // export name
    def export(String name) {
        println(((VariablesBinding) this.getBinding()).getGroovyModel().generateCode(name).toString())
    }



    // disable run method while running
    int count = 0
    abstract void scriptBody()
    def run() {
        if(count == 0) {
            count++
            scriptBody()
        } else {
            println "Run method is disabled"
        }
    }


}
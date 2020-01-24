package fr.unice.polytech.dsl.arduinoml.homemade;

import fr.unice.polytech.dsl.arduinoml.Action;
import fr.unice.polytech.dsl.arduinoml.Actuator;
import fr.unice.polytech.dsl.arduinoml.App;
import fr.unice.polytech.dsl.arduinoml.Condition;
import fr.unice.polytech.dsl.arduinoml.OPERATOR;
import fr.unice.polytech.dsl.arduinoml.SIGNAL;
import fr.unice.polytech.dsl.arduinoml.Sensor;
import fr.unice.polytech.dsl.arduinoml.State;
import fr.unice.polytech.dsl.arduinoml.Transition;
import io.github.mosser.arduinoml.kernel.structural.Operator;

/**
 * Convert aml semantic model to kernel semantic model.
 * @author user
 *
 */
public class KernelConverter {
	
	private io.github.mosser.arduinoml.kernel.App application;
	
	public void convert(App app) {
		application = new io.github.mosser.arduinoml.kernel.App();
		application.setName(app.getName());	
	}
	
	public io.github.mosser.arduinoml.kernel.behavioral.Transition convert(Transition transition){
		
		io.github.mosser.arduinoml.kernel.behavioral.Transition trans =
				new io.github.mosser.arduinoml.kernel.behavioral.Transition();
		
		trans.setNext(convert(transition.getNext(),false));
		trans.setCondition(convert(transition.getCondition()));
		
		//add the transition to the origin state
		State state = transition.getPrevious();
		for(io.github.mosser.arduinoml.kernel.behavioral.State s : application.getStates()) {
			if(s.getName().equals(state.getName())) { 
				s.setTransition(trans);
			}
		}
		
		return trans;
	}
	
	public io.github.mosser.arduinoml.kernel.behavioral.Condition convert(Condition cond){
		io.github.mosser.arduinoml.kernel.behavioral.Condition condK = 
				new io.github.mosser.arduinoml.kernel.behavioral.Condition();
		
		for(Sensor sens: cond.getSensors()) condK.addSensor(convert(sens,false));
		for(OPERATOR op: cond.getOperators()) condK.addOperator(convert(op));
		for(SIGNAL s: cond.getValues()) condK.addValue(convert(s));
		
		return condK;
	}
	
	public io.github.mosser.arduinoml.kernel.behavioral.State convert(State state, boolean add){
		io.github.mosser.arduinoml.kernel.behavioral.State state_k = 
				new io.github.mosser.arduinoml.kernel.behavioral.State();
		
		state_k.setName(state.getName());
		for(Action a : state.getActions()) state_k.addAction(convert(a));
		
		if(add)application.addState(state_k);
		return state_k;
	}
	
	public io.github.mosser.arduinoml.kernel.behavioral.Action convert(Action action){
		io.github.mosser.arduinoml.kernel.behavioral.Action action_k = 
				new io.github.mosser.arduinoml.kernel.behavioral.Action();
		
		for(io.github.mosser.arduinoml.kernel.structural.Brick b : application.getBricks()) {
			if(b.getName().equals(action.getActuator().getName())) { 
				action_k.setActuator((io.github.mosser.arduinoml.kernel.structural.Actuator) b);
			}
		}
		action_k.setValue(convert(action.getValue()));
		
		return action_k;
	}
	
	public io.github.mosser.arduinoml.kernel.structural.Sensor convert(Sensor sens, boolean add){
		io.github.mosser.arduinoml.kernel.structural.Sensor sensor = 
				new io.github.mosser.arduinoml.kernel.structural.Sensor();
		
		sensor.setName(sens.getName());
		sensor.setPin(sens.getPin());
		
		if(add)application.addBrick(sensor);
		return sensor;
	}
	
	public io.github.mosser.arduinoml.kernel.structural.Actuator convert(Actuator act, boolean add){
		io.github.mosser.arduinoml.kernel.structural.Actuator actuator = 
				new io.github.mosser.arduinoml.kernel.structural.Actuator();
		
		actuator.setName(act.getName());
		actuator.setPin(act.getPin());
		
		if(add)application.addBrick(actuator);
		return actuator;
	}
	
	public io.github.mosser.arduinoml.kernel.structural.Operator convert(OPERATOR op){
		switch (op) {
		case AND:
			return Operator.AND;
		default:
			return Operator.OR;
		}
		
	}
	
	public io.github.mosser.arduinoml.kernel.structural.SIGNAL convert(SIGNAL s){
		switch (s) {
		case HIGH:
			return io.github.mosser.arduinoml.kernel.structural.SIGNAL.HIGH;
		default:
			return io.github.mosser.arduinoml.kernel.structural.SIGNAL.LOW;
		}
		
	}
	
	
	public io.github.mosser.arduinoml.kernel.App getConvertedApp(){
		//TODO Temporaire
		application.setInitial(application.getStates().get(0));
		return application;
	}
	
	
}

package fr.unice.polytech.dsl.arduinoml.homemade;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import fr.unice.polytech.dsl.arduinoml.Action;
import fr.unice.polytech.dsl.arduinoml.Actuator;
import fr.unice.polytech.dsl.arduinoml.App;
import fr.unice.polytech.dsl.arduinoml.ArduinomlPackage;
import fr.unice.polytech.dsl.arduinoml.Brick;
import fr.unice.polytech.dsl.arduinoml.Condition;
import fr.unice.polytech.dsl.arduinoml.NamedElement;
import fr.unice.polytech.dsl.arduinoml.Sensor;
import fr.unice.polytech.dsl.arduinoml.State;
import fr.unice.polytech.dsl.arduinoml.Transition;
import fr.unice.polytech.dsl.arduinoml.util.ArduinomlSwitch;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class ArduinomlSwitchPrinter extends ArduinomlSwitch<String> {
	
	private Visitor codeGenerator = new ToWiring();
	private KernelConverter converter = new KernelConverter();
	private io.github.mosser.arduinoml.kernel.App application;
	
	public String caseApp(App app) {
		
		converter.convert(app);
		
		for(Brick b : app.getBricks()) doSwitch(b);
		for(State s : app.getStates()) doSwitch(s);
		for(Condition c : app.getConditions()) doSwitch(c);
		for(Transition t : app.getTransitions()) doSwitch(t);

		converter.getConvertedApp().accept(codeGenerator);
		return codeGenerator.getResult().toString();
		
	}
	
	public String caseActuator(Actuator act) {
		converter.convert(act,true);
		return "";
	}
	
	public String caseSensor(Sensor sens) {
		converter.convert(sens,true);
		return "";
	}
	
	public String caseState(State state) {
		converter.convert(state,true);
		return "";
	}
	
	public String caseTransition(Transition transition) {
		converter.convert(transition);
		return "";
	}
	
	public String caseCondition(Condition condition) {
		converter.convert(condition);
		return "";
	}
	
	public String caseAction(Action action) {
		converter.convert(action);
		return "";
	}
	
	
}

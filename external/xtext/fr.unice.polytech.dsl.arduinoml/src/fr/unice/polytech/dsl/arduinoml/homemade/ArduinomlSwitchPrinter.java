package fr.unice.polytech.dsl.arduinoml.homemade;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import fr.unice.polytech.dsl.arduinoml.Actuator;
import fr.unice.polytech.dsl.arduinoml.App;
import fr.unice.polytech.dsl.arduinoml.ArduinomlPackage;
import fr.unice.polytech.dsl.arduinoml.Brick;
import fr.unice.polytech.dsl.arduinoml.NamedElement;
import fr.unice.polytech.dsl.arduinoml.Sensor;
import fr.unice.polytech.dsl.arduinoml.util.ArduinomlSwitch;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class ArduinomlSwitchPrinter extends ArduinomlSwitch<String> {
	
	private Visitor codeGenerator = new ToWiring();
	private io.github.mosser.arduinoml.kernel.App application;
	
	public String caseApp(App app) {
		application = new io.github.mosser.arduinoml.kernel.App();
		application.setName(app.getName());

		for(Brick b : app.getBricks()) doSwitch(b);

		application.accept(codeGenerator);
		return codeGenerator.getResult().toString();
		
	}
	
	public String caseActuator(Actuator act) {
		io.github.mosser.arduinoml.kernel.structural.Actuator actuator = new io.github.mosser.arduinoml.kernel.structural.Actuator();
		actuator.setName(act.getName());
		actuator.setPin(act.getPin());
		List<io.github.mosser.arduinoml.kernel.structural.Brick> bricks = application.getBricks();
		bricks.add(actuator);
		application.setBricks(bricks);
		return actuator.toString();
	}
	
	public String caseSensor(Sensor sens) {
		io.github.mosser.arduinoml.kernel.structural.Sensor sensor = new io.github.mosser.arduinoml.kernel.structural.Sensor();
		sensor.setName(sens.getName());
		sensor.setPin(sens.getPin());
		List<io.github.mosser.arduinoml.kernel.structural.Brick> bricks = application.getBricks();
		bricks.add(sensor);
		application.setBricks(bricks);
		return sensor.toString();
	}
}

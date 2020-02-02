package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.List;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {

	private final static String CURRENT_STATE = "current_state";

	public ToWiring() {
		this.result = new StringBuffer();
	}

	private void w(String s) {
		result.append(String.format("%s\n",s));
	}
	private void w_in(String s) {
		result.append(String.format("%s",s));
	}

	@Override
	public void visit(App app) {
		w("// Wiring code generated from an ArduinoML model");
		w(String.format("// Application name: %s\n", app.getName()));

		w("void setup(){");
		w("  Serial.begin(9600);");
		for(Brick brick: app.getBricks()){
			brick.accept(this);
		}
		w("}\n");

		w("long time = 0; long debounce = 200;\n");

		for(State state: app.getStates()){
			state.accept(this);
		}

		if (app.getInitial() != null) {
			w("void loop() {");
			w(String.format("  state_%s();", app.getInitial().getName()));
			w("}");
		}
	}

	@Override
	public void visit(Actuator actuator) {
	 	w(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
	}


	@Override
	public void visit(Sensor sensor) {
		w(String.format("  pinMode(%d, INPUT);  // %s [Sensor]", sensor.getPin(), sensor.getName()));
	}

	@Override
	public void visit(State state) {
		w(String.format("void state_%s() {",state.getName()));
		for(Action action: state.getActions()) {
			action.accept(this);
		}

		if (state.getTransition() != null) {
			w("  boolean guard = millis() - time > debounce;");
			context.put(CURRENT_STATE, state);
			state.getTransition().accept(this);
		}
		w("}\n");

	}

	@Override
	public void visit(Transition transition) {

		w_in("if( ");
		transition.getCondition().accept(this);
		w_in(" && guard ) { \n");

		w("    time = millis();");
		w(String.format("    state_%s();",transition.getNext().getName()));

		w("  } else {");
		w(String.format("    state_%s();",((State) context.get(CURRENT_STATE)).getName()));
		w("  }");
	}

	@Override
	public void visit(Action action) {
		switch (action.getActuator().getType()){
			case DIGITAL:
				w(String.format("  digitalWrite(%d,%s);",action.getActuator().getPin(),action.getValue()));
				break;
			case ANALOGICAL:
				w(String.format("  analogWrite(%d,%s);",action.getActuator().getPin(),action.getValue()));
				break;
		}
	}

	@Override
	public void visit(MultipleCondition multipleCondition){
		List<SimpleCondition> simpleConditionList = multipleCondition.getConditionList();
		for(int i=0; i< multipleCondition.getConditionList().size(); ++i){
			SimpleCondition simpleCondition = simpleConditionList.get(i);
			simpleCondition.accept(this);

			if(multipleCondition.getOperators().size() > i) {
				Operator op = multipleCondition.getOperators().get(i);
				w_in((op == Operator.AND) ? " && " : " || ");
			}
		}
	}

	@Override
	public void visit(SimpleCondition simpleCondition){
		StringBuilder stringBuilder = new StringBuilder();
		if(simpleCondition.getSensorType().equals(BrickType.DIGITAL)){
				stringBuilder.append(String.format("digitalRead(%d) == %s",
						simpleCondition.getSens().getPin(), simpleCondition.getValue().toUpperCase()));
		}
		else{
			stringBuilder.append(String.format("analogRead(%d)",simpleCondition.getSens().getPin()));
			stringBuilder.append(simpleCondition.getComparator());
			stringBuilder.append(String.format("%s", simpleCondition.getValue()));
		}
		w_in(stringBuilder.toString());
	}

	public String temperatureConversionFunction(){
		return "float convertTemperature(int value){\n" +
				"  float voltage = value * 5.0;\n" +
				"  voltage /= 1024.0; \n" +
				"  Serial.print(voltage); Serial.println(\" volts\");\n" +
				"  float temperatureC = (voltage - 0.5) * 100 ;\n" +
				"  Serial.print(temperatureC); \n" +
				"  Serial.println(\" degrees C\");\n" +
				"  return temperatureC;\n" +
				" }";
	}
}

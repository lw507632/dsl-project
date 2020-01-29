package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.ArrayList;
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
			w("}\n");
		}

	}

	@Override
	public void visit(Transition transition) {

		w_in("if( ");
		transition.getMultipleCondition().accept(this);
		w_in(" && guard ) { \n");

		w("    time = millis();");
		w(String.format("    state_%s();",transition.getNext().getName()));

		w("  } else {");
		w(String.format("    state_%s();",((State) context.get(CURRENT_STATE)).getName()));
		w("  }");
	}

	@Override
	public void visit(Action action) {
		w(String.format("  digitalWrite(%d,%s);",action.getActuator().getPin(),action.getValue()));
	}

	@Override
	public void visit(Condition condition) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i < condition.getSensors().size(); ++i){
			//TODO : if analog, then analogRead()
			stringBuilder.append(String.format("digitalRead(%d) == %s",
					condition.getSensors().get(i).getPin(), condition.getValues().get(i)));

			if(condition.getOperators().size() > i) {
				Operator op = condition.getOperators().get(i);
				stringBuilder.append((op == Operator.AND) ? " && " : " || ");
			}
		}
		w_in(stringBuilder.toString());
	}

	public void visit(MultipleCondition multipleCondition){
		StringBuilder stringBuilder = new StringBuilder();
		List<SimpleCondition> simpleConditionList = multipleCondition.getConditionList();
		for(int i=0; i< multipleCondition.getConditionList().size(); ++i){
			SimpleCondition simpleCondition = simpleConditionList.get(i);
			if(simpleCondition.getSensorType().equals(SensorType.DIGITAL)){
				stringBuilder.append(String.format("digitalRead(%d) == %s",
						simpleCondition.getSens().getPin(), simpleCondition.getValue()));
			}
			else{
				stringBuilder.append(String.format("analogRead(%d)",simpleCondition.getSens().getPin()));
				stringBuilder.append(simpleCondition.getComparator());
				stringBuilder.append(String.format("%s", simpleCondition.getValue()));
			}

			if(multipleCondition.getOperators().size() > i) {
				Operator op = multipleCondition.getOperators().get(i);
				stringBuilder.append((op == Operator.AND) ? " && " : " || ");
			}
		}
		w_in(stringBuilder.toString());
	}
}

package io.github.mosser.arduinoml.kernel.behavioral;


import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Operator;

import java.util.ArrayList;
import java.util.List;

public class MultipleCondition implements Visitable {
    private List<SimpleCondition> conditionList = new ArrayList<>();
    private List<Operator> operators = new ArrayList<>();


    public void addCondition(SimpleCondition condition) {
        this.conditionList.add(condition);
    }

    public void addOperator(Operator o) {
        this.operators.add(o);
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public List<SimpleCondition> getConditionList() {
        return conditionList;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

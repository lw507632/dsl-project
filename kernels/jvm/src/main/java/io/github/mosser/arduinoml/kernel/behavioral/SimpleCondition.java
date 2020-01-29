package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.Sensor;
import io.github.mosser.arduinoml.kernel.structural.SensorType;

public class SimpleCondition {
    private Comparator comparator;
    private Sensor sens;
    private String value;

    public SimpleCondition(Comparator comparator, Sensor sens, String value) {
        this.comparator = comparator;
        this.sens = sens;
        this.value = value;
    }

    public String getComparator() {
        switch (comparator){
            case NEQUALS:
                return "!=";
            case INFERIOR:
                return "<";
            case SUPERIOR:
                return ">";
            case EQUALS:
                return "==";
        }
        return "==";
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public Sensor getSens() {
        return sens;
    }

    public void setSens(Sensor sens) {
        this.sens = sens;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SensorType getSensorType(){
        return this.sens.getType();
    }
}

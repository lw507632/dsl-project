package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Condition implements Visitable {
    @Override
    public void accept(Visitor visitor) {
        System.out.println("Do nothing");
    }
}

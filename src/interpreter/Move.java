package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;

import java.util.Map;

public class Move extends TurtleExpression {
    private final Map<String, Integer> variables;
    private final Value value;

    public Move(Map<String, Integer> variables, Value value) {
        this.variables = variables;
        this.value = value;
    }

    @Override
    public void interpret(Turtle turtle) {
        turtle.move(value.interpret(variables));
    }

    @Override
    public void accept(TurtleVisitor visitor, Turtle turtle) {
        visitor.visit(this, turtle);
    }

    @Override
    protected void add(TurtleExpression child) {
        // Not used
    }

    public int getValue() {
        return value.interpret(variables);
    }
}
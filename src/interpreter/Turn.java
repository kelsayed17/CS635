package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;

import java.util.Map;

public class Turn extends TurtleExpression {
    private final Map<String, Integer> variables;
    private final Value value;

    public Turn(Map<String, Integer> variables, Value value) {
        this.variables = variables;
        this.value = value;
    }

    @Override
    public void interpret(Turtle turtle) {
        turtle.turn(value.interpret(variables));
    }

    @Override
    public void accept(TurtleVisitor visitor, Turtle turtle) {
        visitor.visit(this, turtle);
    }

    @Override
    protected void add(TurtleExpression child) {
        // Not used
    }
}
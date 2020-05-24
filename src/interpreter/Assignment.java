package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;

import java.util.Map;

public class Assignment extends TurtleExpression {
    private final Map<String, Integer> variables;
    private final String variable;
    private final int value;

    public Assignment(Map<String, Integer> variables, String variable, int value) {
        this.variables = variables;
        this.variable = variable;
        this.value = value;
    }

    @Override
    public void interpret(Turtle context) {
        variables.put(variable, value);
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

package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Repeat extends TurtleExpression {
    private final Queue<TurtleExpression> children = new LinkedList<>();
    private final Map<String, Integer> variables;
    private final Value value;

    public Repeat(Map<String, Integer> variables, Value value) {
        this.variables = variables;
        this.value = value;
    }

    public void add(TurtleExpression child) {
        children.add(child);
        child.parent = this;
    }

    @Override
    public void interpret(Turtle turtle) {
        int count = value.interpret(variables);

        while (count > 0) {
            for (TurtleExpression child : children)
                child.interpret(turtle);
            count--;
        }
    }

    @Override
    public void accept(TurtleVisitor visitor, Turtle turtle) {
        int count = value.interpret(variables);

        while (count > 0) {
            for (TurtleExpression child : children)
                child.accept(visitor, turtle);
            count--;
        }
    }
}
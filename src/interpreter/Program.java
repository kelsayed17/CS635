package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;

import java.util.LinkedList;
import java.util.Queue;

public class Program extends TurtleExpression {
    private final Queue<TurtleExpression> children = new LinkedList<>();

    public Program() {
        this.parent = null;
    }

    public void add(TurtleExpression child) {
        children.add(child);
    }

    @Override
    public void interpret(Turtle turtle) {
        for (TurtleExpression child : children)
            child.interpret(turtle);
    }

    @Override
    public void accept(TurtleVisitor visitor, Turtle turtle) {
        for (TurtleExpression child : children)
            child.accept(visitor, turtle);
    }
}

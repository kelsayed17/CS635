package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;

public class PenDown extends TurtleExpression {
    @Override
    public void interpret(Turtle turtle) {
        turtle.penDown();
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
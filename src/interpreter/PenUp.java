package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;

public class PenUp extends TurtleExpression {
    @Override
    public void interpret(Turtle turtle) {
        turtle.penUp();
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
package interpreter;

import objects.Turtle;
import visitor.TurtleVisitor;
import visitor.Visitable;

public abstract class TurtleExpression implements NonTerminalExpression<Turtle>, Visitable<TurtleVisitor, Turtle> {
    protected TurtleExpression parent;

    protected abstract void add(TurtleExpression child);
}
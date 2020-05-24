package visitor;

import interpreter.*;
import objects.Turtle;

public interface TurtleVisitor {
    void visit(Move move, Turtle turtle);

    void visit(Turn turn, Turtle turtle);

    void visit(PenUp penUp, Turtle turtle);

    void visit(PenDown penDown, Turtle turtle);

    void visit(Assignment assignment, Turtle turtle);
}
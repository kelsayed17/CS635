package visitor;

import interpreter.*;
import objects.Turtle;

public class TurtleDistance implements TurtleVisitor {
    private int totalDistance = 0;

    public int getTotalDistance() {
        return totalDistance;
    }

    @Override
    public void visit(Move move, Turtle turtle) {
        totalDistance += Math.abs(move.getValue());
    }

    @Override
    public void visit(Turn turn, Turtle turtle) {
        turn.interpret(turtle);
    }

    @Override
    public void visit(PenUp penUp, Turtle turtle) {
        penUp.interpret(turtle);
    }

    @Override
    public void visit(PenDown penDown, Turtle turtle) {
        penDown.interpret(turtle);
    }

    @Override
    public void visit(Assignment assignment, Turtle turtle) {
        assignment.interpret(turtle);
    }
}

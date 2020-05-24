package visitor;

import interpreter.*;
import objects.Turtle;

import java.util.ArrayList;
import java.util.List;

public class TurtleStates implements TurtleVisitor {
    private Turtle turtle;
    private final List<Turtle> savedStates = new ArrayList<>();
    private final List<TurtleExpression> savedExpressions = new ArrayList<>();

    public TurtleStates() {
        savedStates.add(new Turtle());
    }

    public Turtle getCurrentState() {
        return turtle;
    }

    public Turtle getStateAt(int index) {
        return savedStates.get(index);
    }

    public List<Turtle> getSavedStates() {
        return savedStates;
    }

    private void preserveState(TurtleExpression turtleExpression, Turtle turtle) {
        Turtle state = new Turtle();
        this.turtle = turtle;

        turtleExpression.interpret(turtle);
        savedExpressions.add(turtleExpression);

        for (TurtleExpression savedExpression : savedExpressions)
            savedExpression.interpret(state);

        savedStates.add(state);
    }

    @Override
    public void visit(Move move, Turtle turtle) {
        preserveState(move, turtle);
    }

    @Override
    public void visit(Turn turn, Turtle turtle) {
        preserveState(turn, turtle);
    }

    @Override
    public void visit(PenUp penUp, Turtle turtle) {
        preserveState(penUp, turtle);
    }

    @Override
    public void visit(PenDown penDown, Turtle turtle) {
        preserveState(penDown, turtle);
    }

    @Override
    public void visit(Assignment assignment, Turtle turtle) {
        assignment.interpret(turtle);
    }
}

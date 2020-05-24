package main;

import interpreter.Evaluator;
import interpreter.TurtleExpression;
import objects.Turtle;
import visitor.TurtleDistance;

import java.io.IOException;

public class InterpreterMain {
    public static void main(String[] args) throws IOException {
        Turtle turtle = new Turtle();
        Evaluator evaluator = new Evaluator("data/turtle4.txt");
        TurtleExpression turtleExpression = evaluator.parse();

        // Use this for states
//        TurtleStates turtleStates = new TurtleStates();
//        turtleExpression.accept(turtleStates, turtle);
//        for (Turtle state : turtleStates.getSavedStates())
//            System.out.println(state);

        // Use this for distance
        TurtleDistance turtleDistance = new TurtleDistance();
        turtleExpression.accept(turtleDistance, turtle);
        System.out.println(turtleDistance.getTotalDistance());

        // Use this for interpret
//        turtleExpression.interpret(turtle);
//        System.out.println(turtle);
    }
}

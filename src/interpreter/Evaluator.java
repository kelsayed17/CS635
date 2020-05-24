package interpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Evaluator {
    private final String fileName;
    private TurtleExpression exp = new Program();
    private final Map<String, Integer> variables = new HashMap<>();

    public Evaluator(String fileName) {
        this.fileName = fileName;
    }

    public TurtleExpression parse() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String currentLine = reader.readLine();

        while (currentLine != null) {
            if (!parse(currentLine))
                throw new IllegalArgumentException();

            currentLine = reader.readLine();
        }

        reader.close();
        return exp;
    }

    private boolean parse(String currentLine) {
        String[] tokens = currentLine.split(" ");

        if (tokens.length != 1 && tokens.length != 2 && tokens.length != 3)
            return false;

        if (tokens.length == 3) {
            if (tokens[0].charAt(0) != '#' || tokens[0].length() < 2)
                return false;

            if (!isNum(tokens[2]))
                return false;

            exp.add(new Assignment(variables, tokens[0], Integer.parseInt(tokens[2])));

            return true;
        }

        switch (tokens[0]) {
            case "move":
                exp.add(new Move(variables, getValue(tokens[1])));
                break;
            case "turn":
                exp.add(new Turn(variables, getValue(tokens[1])));
                break;
            case "penUp":
                exp.add(new PenUp());
                break;
            case "penDown":
                exp.add(new PenDown());
                break;
            case "repeat":
                TurtleExpression repeat = new Repeat(variables, getValue(tokens[1]));
                exp.add(repeat);
                exp = repeat;
                break;
            case "end":
                exp = exp.parent;
                break;
            default:
                return false;
        }

        return true;
    }

    private Value getValue(String value) {
        if (isNum(value))
            return new Value(Integer.parseInt(value));
        else
            return new Value(value);
    }

    private boolean isNum(String num) {
        try {
            Integer.parseInt(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

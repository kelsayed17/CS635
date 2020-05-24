package interpreter;

import java.util.Map;

public class Value implements TerminalExpression<Integer, Map<String, Integer>> {
    private String variable;
    private int value;

    public Value(String variable) {
        this.variable = variable;
    }

    public Value(int value) {
        this.value = value;
    }

    @Override
    public Integer interpret(Map<String, Integer> variables) {
        return variable != null ? variables.get(variable) : value;
    }
}
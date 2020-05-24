package interpreter;

public interface TerminalExpression<T, C> {
    T interpret(C context);
}
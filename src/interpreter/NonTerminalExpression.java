package interpreter;

public interface NonTerminalExpression<E> {
    void interpret(E context);
}
package visitor;

public interface Visitable<E, T> {
    void accept(E visitor, T context);
}
package command;

import java.util.Stack;

public class Invoker {
    private Stack<Command> undoStack = new Stack<>();

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
    }

    public void undo() {
        if (undoStack.isEmpty())
            return;

        Command command = undoStack.pop();
        command.undo();
    }
}
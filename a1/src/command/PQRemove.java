package command;

import objects.PriorityQueue;

public class PQRemove<E> implements Command {
    private PriorityQueue<E> priorityQueue;
    private E element;

    public PQRemove(PriorityQueue<E> priorityQueue, E element) {
        this.priorityQueue = priorityQueue;
        this.element = element;
    }

    @Override
    public void execute() {
        priorityQueue.remove(element);
    }

    @Override
    public void undo() {
        priorityQueue.add(element);
    }
}

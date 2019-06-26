package command;

import objects.PriorityQueue;

public class PQAdd<E> implements Command {
    private PriorityQueue<E> priorityQueue;
    private E element;

    public PQAdd(PriorityQueue<E> priorityQueue, E element) {
        this.priorityQueue = priorityQueue;
        this.element = element;
    }

    @Override
    public void execute() {
        priorityQueue.add(element);
    }

    @Override
    public void undo() {
        priorityQueue.remove(element);
    }
}
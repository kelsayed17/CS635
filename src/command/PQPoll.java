package command;

import objects.PriorityQueue;

public class PQPoll<E> implements Command {
    private final PriorityQueue<E> priorityQueue;
    private E element;

    public PQPoll(PriorityQueue<E> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    @Override
    public void execute() {
        element = priorityQueue.poll();
    }

    @Override
    public void undo() {
        priorityQueue.add(element);
    }
}
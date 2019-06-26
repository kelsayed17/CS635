import command.Invoker;
import command.PQAdd;
import objects.PriorityQueue;
import objects.Student;
import strategy.ComparatorGPA;

import java.util.Iterator;

public class Tester {
    public static void main(String[] args) {


        Student a = new Student(3.46, 40);
        Student b = new Student(3.7, 60);

        PriorityQueue<Student> priorityQueue = new objects.PriorityQueue<>(new ComparatorGPA());

        Invoker invoker = new Invoker();
        invoker.execute(new PQAdd<>(priorityQueue, a));
        invoker.execute(new PQAdd<>(priorityQueue, b));
        //invoker.undo();

        Iterator itr = priorityQueue.iterator();

        System.out.println("Using for each");
        for (Student student : priorityQueue) {
            System.out.println(student);
        }

        System.out.println("Using iterator");
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println(priorityQueue);

    }
}

import command.Invoker;
import command.PQAdd;
import command.PQPoll;
import command.PQRemove;
import objects.PriorityQueue;
import objects.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTests {
    private Student[] values;
    private Student[] sorted;
    private PriorityQueue<Student> priorityQueue;
    private Invoker invoker;

    PriorityQueueTests() {
        super();
    }

    @BeforeEach
    void init() {
        final int CAPACITY = 10;
        final double MAX_GPA = 4.00;
        final double MAX_UNITS = 150.00;

        values = new Student[CAPACITY];
        sorted = new Student[CAPACITY];
        Comparator<Student> comparator = Comparator.comparingDouble(Student::getGpa);
        priorityQueue = new PriorityQueue<>(comparator);
        invoker = new Invoker();

        Random random = new Random();

        for (int i = 0; i < values.length; i++) {

            double randomGPA = random.nextDouble() * MAX_GPA;
            double randomUnits = random.nextDouble() * MAX_UNITS;

            Student student = new Student(randomGPA, randomUnits);
            values[i] = student;
        }

        System.arraycopy(values, 0, sorted, 0, CAPACITY);
        Arrays.sort(sorted, comparator);
    }

    @Test
    void peek() {
        assertNull(priorityQueue.peek());
        Collections.addAll(priorityQueue, values);
        int last = sorted.length - 1;

        for (int i = last; i >= 0; i--) {
            Student maxValue = sorted[i];
            assertEquals(maxValue, priorityQueue.peek());
            priorityQueue.poll();
        }
    }

    @Test
    void add() {
        assertThrows(NullPointerException.class, () -> priorityQueue.add(null));

        Student maxValue = sorted[0];

        for (Student value : values) {
            priorityQueue.add(value);

            if (priorityQueue.comparator().compare(value, maxValue) > 0)
                maxValue = value;

            assertEquals(maxValue, priorityQueue.peek());
        }
    }

    @Test
    void poll() {
        assertNull(priorityQueue.poll());
        Collections.addAll(priorityQueue, values);
        int last = values.length - 1;

        for (int i = last; i >= 0; i--) {
            Student maxValue = sorted[i];
            assertEquals(maxValue, priorityQueue.poll());
        }
        assertNull(priorityQueue.poll());
    }

    @Test
    void iterator() {
        Collections.addAll(priorityQueue, values);
        Object[] objects = priorityQueue.toArray();
        Iterator<Student> iterator = priorityQueue.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(iterator.next(), objects[i]);
            i++;
        }

        int j = 0;
        for (Student student : priorityQueue) {
            assertEquals(student, objects[j]);
            j++;
        }

        priorityQueue.poll();

        //noinspection ResultOfMethodCallIgnored
        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Test
    void isEmpty() {
        assertTrue(priorityQueue.isEmpty());
        Collections.addAll(priorityQueue, values);

        for (Student ignored : values) {
            assertFalse(priorityQueue.isEmpty());
            priorityQueue.poll();
        }

        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, priorityQueue.size());
        Collections.addAll(priorityQueue, values);

        for (int i = values.length; i > 0; i--) {
            assertEquals(i, priorityQueue.size());
            priorityQueue.poll();
        }

        assertEquals(0, priorityQueue.size());
    }

    @Test
    void executeAdd() {
        Student maxValue = sorted[0];

        for (Student value : values) {
            invoker.execute(new PQAdd<>(priorityQueue, value));

            if (priorityQueue.comparator().compare(value, maxValue) > 0)
                maxValue = value;

            assertEquals(maxValue, priorityQueue.peek());
        }
    }

    @Test
    void executePoll() {
        Collections.addAll(priorityQueue, values);
        int last = values.length - 1;

        for (int i = last; i >= 0; i--) {
            Student maxValue = sorted[i];
            assertEquals(maxValue, priorityQueue.peek());
            invoker.execute(new PQPoll<>(priorityQueue));
        }
    }

    @Test
    void executeRemove() {
        Collections.addAll(priorityQueue, values);
        int last = values.length - 1;

        for (int i = last; i >= 0; i--) {
            Student randomValue = values[i];
            invoker.execute(new PQRemove<>(priorityQueue, randomValue));
            assertEquals(priorityQueue.size(), i);
            assertTrue(isHeap(priorityQueue.toArray()));
        }
    }

    @Test
    void undoAdd() {
        Stack<Student> stack = new Stack<>();
        executeAddValues(stack);
        executeRemoveValues(stack);
        undoRemoveValues(stack);
        undoAddValues(stack);
    }

    @Test
    void undoPoll() {
        Collections.addAll(priorityQueue, values);
        Stack<Student> stack = new Stack<>();
        executePollValues(stack);
        executeAddValues(stack);
        undoAddValues(stack);
        //undoRemoveValues(stack);
    }

    @Test
    void undoRemove() {
        Collections.addAll(priorityQueue, values);
        Stack<Student> stack = new Stack<>();
        executeRemoveValues(stack);
        executeAddValues(stack);
        undoAddValues(stack);
        undoRemoveValues(stack);
    }

    private void undoAddValues(Stack<Student> stack) {
        int size = values.length;
        for (Student ignored : values) {
            invoker.undo();
            size--;
            assertEquals(size, priorityQueue.size());
            assertFalse(priorityQueue.contains(stack.pop()));
            assertTrue(isHeap(priorityQueue.toArray()));
        }
    }

    private void undoRemoveValues(Stack<Student> stack) {
        int size = 0;
        for (Student ignored : values) {
            invoker.undo();
            size++;
            assertEquals(size, priorityQueue.size());
            assertTrue(priorityQueue.contains(stack.pop()));
            assertTrue(isHeap(priorityQueue.toArray()));
        }
    }

    private void executeAddValues(Stack<Student> stack) {
        int size = 0;
        for (Student student : values) {
            invoker.execute(new PQAdd<>(priorityQueue, student));
            stack.push(student);
            size++;
            assertEquals(size, priorityQueue.size());
            assertTrue(isHeap(priorityQueue.toArray()));
        }
    }

    private void executePollValues(Stack<Student> stack) {
        int size = values.length;
        for (Student student : values) {
            invoker.execute(new PQPoll<>(priorityQueue));
            stack.push(student);
            size--;
            assertEquals(size, priorityQueue.size());
            assertTrue(isHeap(priorityQueue.toArray()));
        }
    }

    private void executeRemoveValues(Stack<Student> stack) {
        int size = values.length;
        for (Student student : values) {
            invoker.execute(new PQRemove<>(priorityQueue, student));
            stack.push(student);
            size--;
            assertEquals(size, priorityQueue.size());
            assertTrue(isHeap(priorityQueue.toArray()));
        }
    }

    @Test
    void remove() {
        Collections.addAll(priorityQueue, values);
        int last = values.length - 1;

        for (int i = last; i >= 0; i--) {
            Student randomValue = values[i];
            assertTrue(priorityQueue.remove(randomValue));
            assertEquals(priorityQueue.size(), i);
            assertFalse(priorityQueue.contains(randomValue));
            assertTrue(isHeap(priorityQueue.toArray()));
        }
    }

    @Test
    void toArray() {
        Collections.addAll(priorityQueue, values);
        Object[] array = priorityQueue.toArray();
        Iterator<Student> iterator = priorityQueue.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(iterator.next(), array[i]);
            i++;
        }
    }

    private boolean isHeap(Object[] heap) {
        int end = heap.length - 1;

        for (int i = 0; i <= (heap.length - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (hasLeftChild(i, end) && priorityQueue.comparator().compare(getNode(heap, i), getNode(heap, left)) < 0 || hasRightChild(i, end) &&
                    priorityQueue.comparator().compare(getNode(heap, i), getNode(heap, right)) < 0)
                return false;
        }

        return true;
    }

    private Student getNode(Object[] heap, int index) {
        return (Student) heap[index];
    }

    private boolean hasLeftChild(int index, int end) {
        return getLeftChildIndex(index) <= end;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private boolean hasRightChild(int index, int end) {
        return getRightChildIndex(index) <= end;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }
}
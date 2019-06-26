package objects;

import java.util.*;

public class PriorityQueue<E> extends AbstractQueue<E> {
    private static final int CAPACITY = 10;
    private final Comparator<? super E> comparator;
    private Object[] heap;
    private int size = 0;
    private int modCount = 0;

    public PriorityQueue() {
        this(CAPACITY, null);
    }

    public PriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    private PriorityQueue(int initialCapacity, Comparator<? super E> comparator) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.heap = new Object[initialCapacity];
        this.comparator = comparator;
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this(CAPACITY, comparator);
    }

    public Comparator<? super E> comparator() {
        return comparator;
    }

    public boolean add(E e) {
        return offer(e);
    }

    public E remove() {
        if (isEmpty()) throw new NoSuchElementException();
        return poll();
    }

    public E element() {
        if (isEmpty()) throw new NoSuchElementException();
        return peek();
    }

    public boolean offer(E e) {
        if (e == null) throw new NullPointerException();
        if (isFull()) resize();

        int end = size;
        heap[end] = e;
        size++;
        modCount++;
        bubbleUp(end);
        return true;
    }

    public E poll() {
        if (isEmpty()) return null;
        E element = peek();
        removeAt(0);
        return element;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        return isEmpty() ? null : (E) heap[0];
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) return false;

        removeAt(index);
        return true;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    private int indexOf(Object o) {
        if (o == null) return -1;

        for (int i = 0; i < size; i++)
            if (o.equals(heap[i]))
                return i;

        return -1;
    }

    private void removeAt(int index) {
        int end = size - 1;
        swap(index, end);
        heap[end] = null;
        size--;
        modCount++;

        if (index == end) return;
        int parent = hasParent(index) ? getParentIndex(index) : 0;

        if (compareMethod(heap[index], heap[parent]) > 0)
            bubbleUp(index);
        else
            trickleDown(index);
    }

    private void bubbleUp(int index) {
        while (hasParent(index)) {
            int parent = getParentIndex(index);

            if (compareMethod(heap[index], heap[parent]) > 0)
                swap(index, parent);
            else
                break;

            index = parent;
        }
    }

    private void trickleDown(int index) {
        while (hasLeftChild(index)) {
            int max = index;
            int left = getLeftChildIndex(index);
            int right = getRightChildIndex(index);

            if (hasLeftChild(index) && compareMethod(heap[left], heap[max]) > 0)
                max = left;

            if (hasRightChild(index) && compareMethod(heap[right], heap[max]) > 0)
                max = right;

            if (index != max)
                swap(index, max);
            else
                break;

            index = max;
        }
    }

    @SuppressWarnings("unchecked")
    private int compareMethod(Object a, Object b) {
        if (comparator != null)
            return comparator.compare((E) a, (E) b);
        else {
            Comparable<? super E> node = (Comparable<? super E>) a;
            return node.compareTo((E) b);
        }
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) <= size - 1;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) <= size - 1;
    }

    private boolean hasParent(int index) {
        return index != 0;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == heap.length;
    }

    private void swap(int a, int b) {
        Object temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    private void resize() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++)
            heap[i] = null;
        size = 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(heap, size);
    }

    @Override
    public String toString() {
        return Arrays.toString(heap);
    }

    private void heapify() {
        int end = size - 1;
        int parent = getParentIndex(end);
        for (int i = parent; i >= 0; i--)
            trickleDown(i);
    }

    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int expectedModCount = modCount;
            private int index = 0;

            public boolean hasNext() {
                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return index != size;
            }

            public E next() {
                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                if (index == size)
                    throw new NoSuchElementException();
                return (E) heap[index++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
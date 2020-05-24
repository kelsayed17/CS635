package strategy;

import objects.Student;

import java.util.Comparator;

public class ComparatorGPA implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return Double.compare(a.getGpa(), b.getGpa());
    }
}
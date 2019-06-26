package strategy;

import objects.Student;

import java.util.Comparator;

public class ComparatorGPAUnits implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return Double.compare(0.7 * a.getUnits() + 0.3 * a.getGpa(), 0.7 * b.getUnits() + 0.3 * b.getGpa());
    }
}
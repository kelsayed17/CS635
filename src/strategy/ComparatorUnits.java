package strategy;

import objects.Student;

import java.util.Comparator;

public class ComparatorUnits implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return Double.compare(a.getUnits(), b.getUnits());
    }
}
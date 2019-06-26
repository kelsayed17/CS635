package tests;

import objects.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentTest {
    private static final Double LOW_GPA = 0.00;
    private static final Double HIGH_GPA = 4.00;
    private static final Double LOW_UNITS = 0.00;
    private static final Double HIGH_UNITS = 150.00;
    private static final int CAPACITY = 100000;
    private Student student;
    private Double[] gpas;
    private Double[] units;

    StudentTest() {
        super();
    }

    @BeforeEach
    void init() {
        final Double MIN = -1000.00;
        final Double MAX = 1000.00;

        gpas = new Double[CAPACITY];
        units = new Double[CAPACITY];

        // Fill array with random values
        for (int i = 0; i < CAPACITY; i++) {
            Double randomGPA = ThreadLocalRandom.current().nextDouble(MIN, MAX);
            Double randomUnits = ThreadLocalRandom.current().nextDouble(MIN, MAX);
            gpas[i] = randomGPA;
            units[i] = randomUnits;
        }

        student = new Student();
    }

    @Test
    void twoArgConstructor() {
        for (int i = 0; i < CAPACITY; i++) {
            Double gpa = gpas[i];
            Double unit = units[i];
            if (gpa >= LOW_GPA && gpa <= HIGH_GPA && unit >= LOW_UNITS && unit <= HIGH_UNITS) {
                student = new Student(gpa, unit);
                assertTrue(student.getGpa() >= LOW_GPA && student.getGpa() <= HIGH_GPA);
                assertTrue(student.getUnits() >= LOW_UNITS && student.getUnits() <= HIGH_UNITS);
            } else {
                assertThrows(IllegalArgumentException.class, () -> {
                    student = new Student(gpa, unit);
                });
            }
        }
    }

    @Test
    void fiveArgConstructor() {
        for (int i = 0; i < CAPACITY; i++) {
            Double gpa = gpas[i];
            Double unit = units[i];
            if (gpa >= LOW_GPA && gpa <= HIGH_GPA && unit >= LOW_UNITS && unit <= HIGH_UNITS) {
                student = new Student("John Doe", "123456789", "johndoe@sdsu.edu", gpa, unit);
                assertTrue(student.getGpa() >= LOW_GPA && student.getGpa() <= HIGH_GPA);
                assertTrue(student.getUnits() >= LOW_UNITS && student.getUnits() <= HIGH_UNITS);
            } else {
                assertThrows(IllegalArgumentException.class, () -> {
                    student = new Student("John Doe", "123456789", "johndoe@sdsu.edu", gpa, unit);
                });
            }
        }
    }

    @Test
    void setGpa() {
        for (Double gpa : gpas) {
            if (gpa >= LOW_GPA && gpa <= HIGH_GPA) {
                student.setGpa(gpa);
                assertTrue(student.getGpa() >= LOW_GPA && student.getGpa() <= HIGH_GPA);
            } else {
                assertThrows(IllegalArgumentException.class, () -> student.setGpa(gpa));
            }
        }
    }

    @Test
    void setUnits() {
        for (Double unit : units) {
            if (unit >= LOW_UNITS && unit <= HIGH_UNITS) {
                student.setUnits(unit);
                assertTrue(student.getUnits() >= LOW_UNITS && student.getUnits() <= HIGH_UNITS);
            } else {
                assertThrows(IllegalArgumentException.class, () -> student.setUnits(unit));
            }
        }
    }

    @Test
    void getGpa() {
        setGpa();
    }

    @Test
    void getUnits() {
        setUnits();
    }
}

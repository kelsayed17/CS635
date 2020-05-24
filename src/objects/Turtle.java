package objects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Turtle {

    private final Point location = new Point();
    private int degrees = 0;
    private PenStatus penStatus = PenStatus.UP;

    private enum PenStatus {
        UP, DOWN
    }

    public Turtle() {
        this(0, 0);
    }

    public Turtle(int x, int y) {
        this((double) x, (double) y);
    }

    public Turtle(int x, double y) {
        this((double) x, y);
    }

    public Turtle(double x, int y) {
        this(x, (double) y);
    }

    public Turtle(double x, double y) {
        location.setLocation(x, y);
    }

    public void move(int distance) {
        double radians = degrees * Math.PI / 180;
        double deltaX = Math.cos(radians) * distance;
        double deltaY = Math.sin(radians) * distance;
        double newX = location.getX() + deltaX;
        double newY = location.getY() + deltaY;
        location.setLocation(newX, newY);
    }

    public void turn(int degrees) {
        this.degrees += degrees;
    }

    public void penUp() {
        penStatus = PenStatus.UP;
    }

    public void penDown() {
        penStatus = PenStatus.DOWN;
    }

    public boolean isPenUp() {
        return penStatus == PenStatus.UP;
    }

    public int direction() {
        return degrees;
    }

    public Point location() {
        return location;
    }

    static class Point {
        private double x;
        private double y;

        public double getX() {
            return round(x);
        }

        public double getY() {
            return round(y);
        }

        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }

        private double round(double value) {
            BigDecimal bigDecimal = new BigDecimal(value);
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            return bigDecimal.doubleValue();
        }
    }

    @Override
    public String toString() {
        return "Location: (" + location.getX() + ", " + location.getY() + ")\tDirection: " + degrees + "\tPen Status: " + penStatus;
    }
}
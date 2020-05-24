package objects;

public class Student {
    private String name = "John Doe";
    private String redId = "123456789";
    private String email = "johndoe@sdsu.edu";
    private double gpa = 0.00;
    private double units = 0.00;

    public Student() {

    }

    public Student(double gpa, double units) {
        setGpa(gpa);
        setUnits(units);
    }

    public Student(String name, String redId, String email, double gpa, double units) {
        this.name = name;
        this.redId = redId;
        this.email = email;
        setGpa(gpa);
        setUnits(units);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedId() {
        return redId;
    }

    public void setRedId(String redId) {
        this.redId = redId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa >= 0.00 && gpa <= 4.00)
            this.gpa = gpa;
        else
            throw new IllegalArgumentException("GPA must be between 0.00 and 4.00");
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        if (units >= 0.00 && units <= 150.00)
            this.units = units;
        else
            throw new IllegalArgumentException("Units must be between 0.00 and 150.00");
    }

    @Override
    public String toString() {
        return String.valueOf(gpa);
    }
}
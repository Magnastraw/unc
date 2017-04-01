package src.xmltest.content;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlRootElement(name = "student")
@XmlType(propOrder = {"id", "firstName", "lastName", "birthDate", "course"})
public class Student {
    private int id;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String course;

    public Student() {
    }

    public Student(String birthDate, String firstName, String lastName, String course) {
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

    public Student(int id, String birthDate, String firstName, String lastName, String course) {
        this.id = id;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "\nid:" + id + "\nfirstName:" + firstName + "\nlastName:" + lastName + "\nbirthDate:" + birthDate + "\ncourse:" + course;
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

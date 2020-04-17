package it.polito.ai.laboratorio2.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    private String name;
    private int min;
    private int max;
    private boolean enabled;
    @ManyToMany(mappedBy="courses")
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if(!this.students.contains(student))
            this.students.add(student);
        if(!student.getCourses().contains(this))
            student.addCourse(this);
    }


    @Override
    public String toString() {
        return this.name + " " + this.min + " " + this.max;
    }
}

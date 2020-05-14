package it.polito.ai.laboratorio2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Teacher {

    @Id
    private String email;
    private String name;
    private String firstName;
    @OneToMany()
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "teacher_email"), inverseJoinColumns = @JoinColumn(name = "course_name") )
    private List<Course> course = null;

    public void addCourse(Course course) {
        if(this.course.contains(course))
            this.course.add(course);
        if(course.getTeacher() != this)
            course.setTeacher(this);
    }
}

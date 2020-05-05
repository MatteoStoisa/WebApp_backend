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
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "teacher_email"), inverseJoinColumns = @JoinColumn(name = "course_name") )
    private Course course = null;

    public void addCourse(Course course) {
        if(this.course != course)
            this.course = course;
        if(course.getTeacher() != this)
            course.setTeacher(this);
    }
}

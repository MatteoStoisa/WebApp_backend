package it.polito.ai.laboratorio2.entities;

import it.polito.ai.laboratorio2.services.exceptions.TeamInvalidMembersNumberException;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int status;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "team_student", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "student_id") )
    private List<Student> members = new ArrayList<>();

    public void selectCourse(Course course) {
        if(!this.course.equals(course))
            this.course = course;
        if(!course.getTeams().contains(this))
            course.addTeam(this);
    }

    public void unselectCourse(Course course) {
        if(this.course.equals(course))
            this.course = null;
        if(course.getTeams().contains(this))
            course.removeTeam(this);
    }

    public void addMember(Student student) {
        if(!this.members.contains(student))
            this.members.add(student);
        if(!student.getTeams().contains(this))
            student.addTeam(this);
    }

    public void removeMember(Student student) {
        if(this.members.contains(student))
            this.members.remove(student);
        if(student.getTeams().contains(this))
            student.removeTeam(this);
    }

}

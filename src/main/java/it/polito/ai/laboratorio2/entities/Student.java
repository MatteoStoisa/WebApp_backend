package it.polito.ai.laboratorio2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Student {

    @Id
    private String id;
    private String name;
    private String firstName;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_name") )
    private List<Course> courses = new ArrayList<>();
    @ManyToMany(mappedBy = "members")
    private List<Team> teams = new ArrayList<>();

    public void addCourse(Course course) {
        if(!this.courses.contains(course))
            this.courses.add(course);
        if(!course.getStudents().contains(this))
            course.addStudent(this);
    }

    public void addTeam(Team team) {
        if(!this.teams.contains(team))
            this.teams.add(team);
        if(!team.getMembers().contains(this))
            team.addMember(this);
    }

    public void removeTeam(Team team) {
        if(this.teams.contains(team))
            this.teams.remove(team);
        if(team.getMembers().contains(this))
            team.removeMember(this);
    }

    @Override
    public String toString() {
        return this.id + " " + this.name + " " + this.firstName;
    }
}

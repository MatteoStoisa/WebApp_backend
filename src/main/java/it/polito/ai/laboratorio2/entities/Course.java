package it.polito.ai.laboratorio2.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private List<Team> teams = new ArrayList<>();

    public void addStudent(Student student) {
        if(!this.students.contains(student))
            this.students.add(student);
        if(!student.getCourses().contains(this))
            student.addCourse(this);
    }

    public void addTeam(Team team) {
        if(!this.teams.contains(team))
            this.teams.add(team);
        if(!team.getCourse().equals(this))
            team.setCourse(this);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
        if(team.getCourse().equals(this))
            team.setCourse(null);
    }


    @Override
    public String toString() {
        return "Course: "+this.name+" "+this.min+" "+ this.max+")";
    }
}

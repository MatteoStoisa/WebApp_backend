package it.polito.ai.laboratorio2.entities;

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
    private int status; //0: proposed - 1: confirmed
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "team_student", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "student_id") )
    private List<Student> members = new ArrayList<>();

    public void setCourse(Course course) { //puo' essere passato null per rimuovere il corso ma nessuno lo passa mai
        if(course == null && this.course != null) {
            if(!this.course.getTeams().contains(this))
                this.course.removeTeam(this);
            this.course = null;
        }
        else {
            if(this.course == null) {
                this.course = course;
            }
            else {
                if(!this.course.equals(course))
                    this.course = course;
            }
            if(!course.getTeams().contains(this))
                course.addTeam(this);
        }
    }

    public void addMember(Student student) {
        if(!this.members.contains(student))
            this.members.add(student);
        if(!student.getTeams().contains(this))
            student.addTeam(this);
    }

    public void removeMember(Student student) {
        this.members.remove(student);
        if(student.getTeams().contains(this))
            student.removeTeam(this);
    }

    @Override
    public String toString() {
        return "(Team: "+this.name+" "+this.course.getName()+")";
    }

}

package it.polito.ai.laboratorio2.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Course {

    @Id
    private String name;
    private int min;
    private int max;
}

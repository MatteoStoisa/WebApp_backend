package it.polito.ai.laboratorio2.dtos;

import lombok.Data;

import javax.persistence.Id;

@Data
public class CourseDTO {
    @Id
    private String name;
    private int min;
    private int max;
    private boolean enabled;
}

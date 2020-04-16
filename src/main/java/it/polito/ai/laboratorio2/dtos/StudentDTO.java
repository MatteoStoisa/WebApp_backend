package it.polito.ai.laboratorio2.dtos;

import lombok.Data;

import javax.persistence.Id;

@Data
public class StudentDTO {
    @Id
    private String id;
    private String name;
    private String firstName;
}

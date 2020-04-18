package it.polito.ai.laboratorio2.dtos;

import lombok.Data;

import javax.persistence.Id;

@Data
public class TeamDTO {
    @Id
    private Long id;
    private String name;
    private int status;
}

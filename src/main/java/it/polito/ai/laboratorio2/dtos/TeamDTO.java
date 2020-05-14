package it.polito.ai.laboratorio2.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class TeamDTO extends RepresentationModel<CourseDTO> {
    private Long id;
    private String name;
    private int status;
}

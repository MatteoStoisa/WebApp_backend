package it.polito.ai.laboratorio2.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class TeacherDTO extends RepresentationModel<CourseDTO> {
    private String email;
    private String name;
    private String firstName;
}

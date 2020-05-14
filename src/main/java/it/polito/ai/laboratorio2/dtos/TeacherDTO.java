package it.polito.ai.laboratorio2.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TeacherDTO extends RepresentationModel<CourseDTO> {
    @NotEmpty
    @NotBlank
    @NotNull
    private String email;
    private String name;
    private String firstName;
}

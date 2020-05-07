package it.polito.ai.laboratorio2.dtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CourseDTO extends RepresentationModel<CourseDTO> {
    @NotEmpty
    @NotBlank
    @NotNull
    private String name;
    private int min;
    private int max;
    private boolean enabled;
}

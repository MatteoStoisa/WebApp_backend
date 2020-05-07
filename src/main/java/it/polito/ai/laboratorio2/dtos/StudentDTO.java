package it.polito.ai.laboratorio2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class StudentDTO extends RepresentationModel<CourseDTO> {
    @CsvBindByName
    @NotEmpty
    @NotBlank
    @NotNull
    private String id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String firstName;
}

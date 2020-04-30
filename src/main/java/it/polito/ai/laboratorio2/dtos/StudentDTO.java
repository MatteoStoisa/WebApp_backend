package it.polito.ai.laboratorio2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class StudentDTO extends RepresentationModel<CourseDTO> {
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String firstName;
}

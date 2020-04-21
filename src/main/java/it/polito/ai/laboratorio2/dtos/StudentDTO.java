package it.polito.ai.laboratorio2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.Id;

@Data
public class StudentDTO {
    @Id
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String firstName;
}

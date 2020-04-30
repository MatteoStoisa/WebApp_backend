package it.polito.ai.laboratorio2;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.entities.Course;
import it.polito.ai.laboratorio2.entities.Student;
import it.polito.ai.laboratorio2.services.TeamService;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Log(topic = "Laboratorio2Application")
public class Laboratorio2Application {

    public static void main(String[] args) {
        SpringApplication.run(Laboratorio2Application.class, args);
    }
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}



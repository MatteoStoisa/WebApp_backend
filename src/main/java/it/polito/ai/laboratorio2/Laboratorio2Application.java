package it.polito.ai.laboratorio2;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.entities.Course;
import it.polito.ai.laboratorio2.entities.Student;
import it.polito.ai.laboratorio2.repositories.CourseRepository;
import it.polito.ai.laboratorio2.repositories.StudentRepository;
import it.polito.ai.laboratorio2.services.TeamService;
import it.polito.ai.laboratorio2.services.TeamServiceImpl;
import it.polito.ai.laboratorio2.services.exceptions.CourseNotFoundException;
import it.polito.ai.laboratorio2.services.exceptions.StudentNotFoundException;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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

    @Bean
    CommandLineRunner runner(@Autowired TeamService teamService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                /* //csv debug
                try {
                    ClassPathResource classPathResource = new ClassPathResource("static\\upload_example.csv");
                    Reader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
                    for (Boolean bool : teamService.addAndEroll(reader, "Analisi 1") )
                        log.info(bool.toString());
                } catch (Exception e) {
                    log.warning("Exception occurred: " + e.toString());
                }
                */
            }
        };
    }


}

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Laboratorio2Application {

    public static void main(String[] args) {
        SpringApplication.run(Laboratorio2Application.class, args);
    }

    @Bean
    CommandLineRunner runner(@Autowired TeamService teamService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                try {
                    teamService.addStudentToCourse("s263138", "Programmazione Di Sistema");
                    teamService.getEnrolledStudents("Programmazione Di Sistema").forEach(c -> System.out.println(c.toString()));
                } catch (CourseNotFoundException | StudentNotFoundException e) {
                    System.out.println("ERROR!!!!");
                }

            }
        };
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

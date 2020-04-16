package it.polito.ai.laboratorio2;

import it.polito.ai.laboratorio2.entities.Course;
import it.polito.ai.laboratorio2.entities.Student;
import it.polito.ai.laboratorio2.repositories.CourseRepository;
import it.polito.ai.laboratorio2.repositories.StudentRepository;
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
    CommandLineRunner runner(CourseRepository courseRepository, StudentRepository studentRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                Course c = new Course();
                c.addStudent(new Student());
                for (Course course : courseRepository.findAll()) {
                    System.out.println(course.toString());
                }
                for (Student student : studentRepository.findAll()) {
                    System.out.println(student.toString());
                }
            }
        };
    }

}

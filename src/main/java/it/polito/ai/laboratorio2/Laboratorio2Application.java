package it.polito.ai.laboratorio2;

import it.polito.ai.laboratorio2.entities.Student;
import it.polito.ai.laboratorio2.entities.Teacher;
import it.polito.ai.laboratorio2.entities.User;
import it.polito.ai.laboratorio2.repositories.StudentRepository;
import it.polito.ai.laboratorio2.repositories.TeacherRepository;
import it.polito.ai.laboratorio2.repositories.UserRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    /*
    DEFINED USERS:
    -   123456              studentPassword1    STUDENT_ROLE
    -   234567              studentPassword2    STUDENT_ROLE
    -   teacher1@mail.it    teacherPassword1    TEACHER_ROLE
    -   adminUsername       adminPassword       ADMIN_ROLE
     */
/*
    @Component
    public class DataInitializer implements CommandLineRunner {
        @Autowired
        UserRepository users;
        @Autowired
        StudentRepository studentRepository;
        @Autowired
        TeacherRepository teacherRepository;
        @Autowired
        PasswordEncoder passwordEncoder;
        @Override
        public void run(String... args) throws Exception {
            //STUDENT1
            Student student1 = new Student();
            student1.setId("34567");
            student1.setName("studentName3");
            student1.setFirstName("studentFirstName3");
            this.studentRepository.save(student1);
            this.users.save(User.builder()
                    .username(student1.getId())
                    .password(this.passwordEncoder.encode("studentPassword3"))
                    .roles(Arrays.asList( "ROLE_STUDENT"))
                    .build()
            );
            //STUDENT2
            Student student2 = new Student();
            student2.setId("234567");
            student2.setName("studentName2");
            student2.setFirstName("studentFirstName2");
            this.studentRepository.save(student2);
            this.users.save(User.builder()
                    .username(student2.getId())
                    .password(this.passwordEncoder.encode("studentPassword2"))
                    .roles(Arrays.asList("ROLE_STUDENT"))
                    .build()
            );
            //TEACHER
            Teacher teacher1 = new Teacher();
            teacher1.setEmail("teacher1@mail.it");
            teacher1.setName("teacherName1");
            teacher1.setFirstName("teacherFirstName1");
            this.teacherRepository.save(teacher1);
            this.users.save(User.builder()
                    .username(teacher1.getEmail())
                    .password(this.passwordEncoder.encode("teacherPassword1"))
                    .roles(Arrays.asList("ROLE_TEACHER"))
                    .build()
            );
            //ADMIN
            this.users.save(User.builder()
                    .username("adminUsername")
                    .password(this.passwordEncoder.encode("adminPassword"))
                    .roles(Arrays.asList("ROLE_ADMIN"))
                    .build()
            );
        }
    }
*/
}



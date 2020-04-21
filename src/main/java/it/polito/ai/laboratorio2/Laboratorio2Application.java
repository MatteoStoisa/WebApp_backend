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

    @Bean
    CommandLineRunner runner(@Autowired TeamService teamService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                performSomeFunctionTest(teamService);
            }
        };
    }

    public void performSomeFunctionTest(TeamService teamService) {
        try {
            /* //csv debug
            ClassPathResource classPathResource = new ClassPathResource("static\\upload_example.csv");
            Reader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
            for (Boolean bool : teamService.addAndEnroll(reader, "Analisi 1") )
                log.info(bool.toString());
            */

            /* // TeamServiceImpl debug
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setName("ML");
            courseDTO.setMin(4);
            courseDTO.setMax(4);
            courseDTO.setEnabled(true);
            log.info(teamService.addCourse(courseDTO).toString());
            log.info(teamService.getCourse("AI").toString());
            log.info(teamService.getAllCourses().toString());
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId("s263138");
            studentDTO.setName("Ivan");
            studentDTO.setFirstName("Murabito");
            log.info(teamService.addStudent(studentDTO).toString());
            log.info(teamService.getStudent("s265542").toString());
            log.info(teamService.getAllStudents().toString());
            log.info(teamService.addStudentToCourse("s265542", "PDS").toString());
            log.info(teamService.getEnrolledStudents("PDS").toString());
            teamService.enableCourse("AI");
            teamService.disableCourse("ML");
            List<StudentDTO> listStudentDTO = new ArrayList<>();
            StudentDTO studentDTO1 = new StudentDTO();
            studentDTO1.setId("s1");
            studentDTO1.setName("name1");
            studentDTO1.setFirstName("firstName1");
            StudentDTO studentDTO2 = new StudentDTO();
            studentDTO2.setId("s2");
            studentDTO2.setName("name2");
            studentDTO2.setFirstName("firstName2");
            listStudentDTO.add(studentDTO1);
            listStudentDTO.add(studentDTO2);
            log.info(teamService.addAll(listStudentDTO).toString());
            List<String> listStudentId = new ArrayList<>();
            listStudentId.add("s1");
            listStudentId.add("aaa");
            log.info(teamService.enrollAll(listStudentId, "PDS").toString());
            log.info(teamService.getCourses("s265542").toString());
            List<String> listStudentId = new ArrayList<>();
            listStudentId.add("s265542");
            listStudentId.add("s263138");
            log.info(teamService.proposeTeam("PDS", "team2", listStudentId).toString());
            log.info(teamService.getTeamsForStudent("s1").toString());
            log.info(teamService.getMembers((long) 5).toString());
            log.info(teamService.getTeamForCourse("PDS").toString());
            log.info(teamService.getStudentsInTeams("PDS").toString());
            log.info(teamService.getAvailableStudents("AI").toString());*/
        } catch (Exception exception) {
            log.warning("OH NO! |*.*| Exception occurred -> " + exception.toString());
        }
    }

}



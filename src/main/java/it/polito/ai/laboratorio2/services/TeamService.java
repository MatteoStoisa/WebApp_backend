package it.polito.ai.laboratorio2.services;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.dtos.TeacherDTO;
import it.polito.ai.laboratorio2.dtos.TeamDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Reader;
import java.util.List;
import java.util.Optional;

public interface TeamService {
    Boolean addCourse(CourseDTO course);
    Optional<CourseDTO> getCourse(String name);
    List<CourseDTO> getAllCourses();
    Boolean addStudent(StudentDTO student);
    @PreAuthorize("(authentication.principal.username.equals(#studentId) && hasRole('STUDENT_ROLE')) || hasRole('ADMIN_ROLE')")
    //@PreAuthorize("hasRole('ADMIN_ROLE')")
    //@PreAuthorize("hasRole('STUDENT_ROLE')")
    Optional<StudentDTO> getStudent(String studentId);
    List<StudentDTO> getAllStudents();
    Boolean addTeacher(TeacherDTO teacherDTO);
    Optional<TeacherDTO> getTeacher(String email);
    List<TeacherDTO> getAllTeachers();
    List<StudentDTO> getEnrolledStudents(String courseName);
    Boolean addStudentToCourse(String studentId, String courseName);
    void enableCourse(String courseName);
    void disableCourse(String courseName);
    List<Boolean> addAll(List<StudentDTO> students);
    List<Boolean> enrollAll(List<String> studentIds, String courseName);
    List<Boolean> addAndEnroll(Reader r, String courseName);
    List<CourseDTO> getCourses(String studentId);
    List<TeamDTO> getTeamsForStudent(String studentId);
    List<StudentDTO>getMembers(Long teamId);
    TeamDTO proposeTeam(String courseId, String name, List<String> memberIds);
    List<TeamDTO> getTeamForCourse(String courseName);
    List<StudentDTO> getStudentsInTeams(String courseName);
    List<StudentDTO> getAvailableStudents(String courseName);
    void setTeamStatus(Long teamId, int status);
    void evictTeam(Long teamId);
}

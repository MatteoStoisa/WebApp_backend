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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    Boolean addCourse(CourseDTO course);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')")
    Optional<CourseDTO> getCourse(String name);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')")
    List<CourseDTO> getAllCourses();
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    Boolean addStudent(StudentDTO student);
    @PreAuthorize("hasRole('ROLE_ADMIN') or (#studentId == authentication.principal.username and hasRole('ROLE_STUDENT'))")
    Optional<StudentDTO> getStudent(String studentId);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    List<StudentDTO> getAllStudents();
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Boolean addTeacher(TeacherDTO teacherDTO);
    @PreAuthorize("hasRole('ROLE_ADMIN') or (#email == authentication.principal.username and hasRole('ROLE_TEACHER'))")
    Optional<TeacherDTO> getTeacher(String email);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<TeacherDTO> getAllTeachers();
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    List<StudentDTO> getEnrolledStudents(String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    Boolean addStudentToCourse(String studentId, String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    void enableCourse(String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    void disableCourse(String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    List<Boolean> addAll(List<StudentDTO> students);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    List<Boolean> enrollAll(List<String> studentIds, String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    List<Boolean> addAndEnroll(Reader r, String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or (#studentId == authentication.principal.username and hasRole('ROLE_STUDENT'))")
    List<CourseDTO> getCourses(String studentId);
    @PreAuthorize("hasRole('ROLE_ADMIN') or (#studentId == authentication.principal.username and hasRole('ROLE_STUDENT'))")
    List<TeamDTO> getTeamsForStudent(String studentId);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
    List<StudentDTO>getMembers(Long teamId);
    @PreAuthorize("hasRole('ROLE_ADMIN') or (#memberIds.contains(authentication.principal.username) and hasRole('ROLE_STUDENT'))")
    TeamDTO proposeTeam(String courseId, String name, List<String> memberIds);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')")
    List<TeamDTO> getTeamForCourse(String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')")
    List<StudentDTO> getStudentsInTeams(String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')")
    List<StudentDTO> getAvailableStudents(String courseName);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
    void setTeamStatus(Long teamId, int status);
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
    void evictTeam(Long teamId);
}

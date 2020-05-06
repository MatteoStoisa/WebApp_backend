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
    Optional<CourseDTO> getCourse(String name);
    List<CourseDTO> getAllCourses();
    Boolean addStudent(StudentDTO student);
    @PreAuthorize("hasRole('ROLE_ADMIN') or (#studentId == authentication.principal.username and hasRole('ROLE_STUDENT'))")
    Optional<StudentDTO> getStudent(String studentId);
    List<StudentDTO> getAllStudents();
    Boolean addTeacher(TeacherDTO teacherDTO);
    Optional<TeacherDTO> getTeacher(String email);
    List<TeacherDTO> getAllTeachers();
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
    List<StudentDTO>getMembers(Long teamId);
    @PreAuthorize("hasRole('ROLE_ADMIN') or (#memberIds.contains(authentication.principal.username) and hasRole('ROLE_STUDENT'))")
    TeamDTO proposeTeam(String courseId, String name, List<String> memberIds);
    List<TeamDTO> getTeamForCourse(String courseName);
    List<StudentDTO> getStudentsInTeams(String courseName);
    List<StudentDTO> getAvailableStudents(String courseName);
    //TODO: test NotificationController access without authentication
    void setTeamStatus(Long teamId, int status);
    void evictTeam(Long teamId);
}

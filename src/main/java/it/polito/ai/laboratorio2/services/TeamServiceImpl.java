package it.polito.ai.laboratorio2.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.dtos.TeacherDTO;
import it.polito.ai.laboratorio2.dtos.TeamDTO;
import it.polito.ai.laboratorio2.entities.*;
import it.polito.ai.laboratorio2.repositories.*;
import it.polito.ai.laboratorio2.services.exceptions.courseException.CourseNotEnabledException;
import it.polito.ai.laboratorio2.services.exceptions.courseException.CourseNotFoundException;
import it.polito.ai.laboratorio2.services.exceptions.studentException.StudentAlreadyInTeamException;
import it.polito.ai.laboratorio2.services.exceptions.studentException.StudentNotFoundException;
import it.polito.ai.laboratorio2.services.exceptions.studentException.StudentNotInCourseException;
import it.polito.ai.laboratorio2.services.exceptions.teamException.TeamMinMaxException;
import it.polito.ai.laboratorio2.services.exceptions.teamException.TeamNotFoundException;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Log(topic = "TeamServiceImpl")
public class TeamServiceImpl implements TeamService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Boolean addCourse(CourseDTO course) {
        if(course.getName() == null)
            return false;
        if(courseRepository.findById(course.getName()).isPresent() || course.getName().equals("") || course.getMin() < 0 || course.getMax() < course.getMin())
            return false;
        //TODO: have to add teacher (API caller) to course.teacher (entity)
        courseRepository.save(modelMapper.map(course, Course.class));
        return true;
    }

    @Override
    public Optional<CourseDTO> getCourse(String name) {
        return courseRepository.findAll()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .map(c -> modelMapper.map(c, CourseDTO.class));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean addStudent(StudentDTO student) {
        if(student.getId() == null)
            return false;
        if(studentRepository.findById(student.getId()).isPresent() || student.getId().equals(""))
            return false;
        User user = new User();
        user.setUsername(student.getId());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = StringUtils.left(UUID.randomUUID().toString(), 8);
        log.info(password + " generated password");
        //TODO: send email with generated password
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Arrays.asList( "STUDENT_ROLE"));
        userRepository.save(user);
        studentRepository.save(modelMapper.map(student, Student.class));
        return true;
    }

    @Override
    public Optional<StudentDTO> getStudent(String studentId) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getId().equals(studentId))
                .findFirst()
                .map(s -> modelMapper.map(s, StudentDTO.class));
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(s -> modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean addTeacher(TeacherDTO teacherDTO) {
        if(teacherDTO.getEmail() == null)
            return false;
        if(teacherRepository.findById(teacherDTO.getEmail()).isPresent() || teacherDTO.getEmail().equals(""))
            return false;
        User user = new User();
        user.setUsername(teacherDTO.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = StringUtils.left(UUID.randomUUID().toString(), 8);
        log.info(password + " generated password");
        //TODO: send email with generated password
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Arrays.asList( "TEACHER_ROLE"));
        userRepository.save(user);
        teacherRepository.save(modelMapper.map(teacherDTO, Teacher.class));
        return true;
    }

    @Override
    public Optional<TeacherDTO> getTeacher(String email) {
        return teacherRepository.findAll()
                .stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .map(t -> modelMapper.map(t, TeacherDTO.class));
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(t -> modelMapper.map(t, TeacherDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getEnrolledStudents(String courseName) throws CourseNotFoundException {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException();
        return courseRepository.getOne(courseName).getStudents()
                .stream()
                .map(s -> modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean addStudentToCourse(String studentId, String courseName) throws StudentNotFoundException, CourseNotFoundException{
        try {
            if(!courseRepository.findById(courseName).isPresent())
                throw new CourseNotFoundException();
            if(!studentRepository.findById(studentId).isPresent())
                throw new StudentNotFoundException();
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            return false;
        }
        if(!courseRepository.getOne(courseName).isEnabled())
            return false;
        courseRepository.getOne(courseName).addStudent(studentRepository.getOne(studentId));
        return true;
    }

    @Override
    public void enableCourse(String courseName) {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException();
        courseRepository.getOne(courseName).setEnabled(true);
    }

    @Override
    public void disableCourse(String courseName) {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException();
        courseRepository.getOne(courseName).setEnabled(false);
    }

    @Override
    public List<Boolean> addAll(List<StudentDTO> students) {
        List<Boolean> booleans = new ArrayList<Boolean>();
        for(StudentDTO studentDTO : students) {
            booleans.add(addStudent(studentDTO));
        }
        return booleans;
    }

    @Override
    public List<Boolean> enrollAll(List<String> studentIds, String courseName) {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException();
        List<Boolean> booleans = new ArrayList<Boolean>();
        for(String studentId : studentIds) {
            booleans.add(addStudentToCourse(studentId, courseName));
        }
        return booleans;
    }

    @Override
    public List<Boolean> addAndEnroll(Reader r, String courseName) {
        /*
        .csv format required:
            id,firstName,name
            s265542,aaa,bbb
            [...]
         */
        CsvToBean<StudentDTO> csvToBean = new CsvToBeanBuilder(r)
                .withType(StudentDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<StudentDTO> students = csvToBean.parse();

        List<Boolean> booleans = addAll(students);
        try {
            booleans.addAll(enrollAll(students.stream().map(StudentDTO::getId).collect(Collectors.toList()), courseName));
        } catch (CourseNotFoundException e) {
            booleans.add(false);
        }
        return booleans;
    }

    @Override
    public List<CourseDTO> getCourses(String studentId) {
        if(!studentRepository.findById(studentId).isPresent())
            throw new StudentNotFoundException();
        return studentRepository.getOne(studentId).getCourses()
                .stream()
                .map(c -> modelMapper.map(c, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamDTO> getTeamsForStudent(String studentId) {
        if(!studentRepository.findById(studentId).isPresent())
            throw new StudentNotFoundException();
        return studentRepository.getOne(studentId).getTeams()
                .stream()
                .map(m -> modelMapper.map(m, TeamDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getMembers(Long teamId) {
        if(!teamRepository.findById(teamId).isPresent())
            throw new TeamNotFoundException();
        return teamRepository.getOne(teamId).getMembers()
                .stream()
                .map(t -> modelMapper.map(t, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO proposeTeam(String courseId, String name, List<String> memberIds) {
        if(!courseRepository.findById(courseId).isPresent())
            throw new CourseNotFoundException();
        if(!courseRepository.getOne(courseId).isEnabled())
            throw new CourseNotEnabledException();
        if(memberIds.size() < courseRepository.getOne(courseId).getMin() || memberIds.size() > courseRepository.getOne(courseId).getMax())
            throw new TeamMinMaxException();
        for(String memberId : memberIds) {
            if(!studentRepository.findById(memberId).isPresent())
                throw new StudentNotFoundException();
            if(!studentRepository.getOne(memberId).getCourses().contains(courseRepository.getOne(courseId)))
                throw new StudentNotInCourseException();
            List<Team> teams = studentRepository.getOne(memberId).getTeams();
            for(Team team : teams) {
                if(team.getCourse().equals(courseRepository.getOne(courseId)))
                    throw new StudentAlreadyInTeamException();
            }
        }
        Team team = new Team();
        team.setName(name);
        team.setStatus(0);
        team.setCourse(courseRepository.getOne(courseId));
        for (String memberId : memberIds) {
            team.addMember(studentRepository.getOne(memberId));
        }
        System.out.println(team.toString());
        teamRepository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Override
    public List<TeamDTO> getTeamForCourse(String courseName) {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException();
        return courseRepository.getOne(courseName).getTeams()
                .stream()
                .map(t -> modelMapper.map(t, TeamDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getStudentsInTeams(String courseName) {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException();
        return courseRepository.getStudentsInTeams(courseName)
                .stream()
                .map(s -> modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getAvailableStudents(String courseName) {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException();
        return courseRepository.getStudentsNotInTeams(courseName)
                .stream()
                .map(s -> modelMapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setTeamStatus(Long teamId, int status) {
        if(teamRepository.findById(teamId).isPresent()) {
            teamRepository.getOne(teamId).setStatus(status);
        }
    }

    @Override
    public void evictTeam(Long teamId) {
        if(teamRepository.findById(teamId).isPresent()) {
            teamRepository.delete(teamRepository.getOne(teamId));
        }
    }
}

package it.polito.ai.laboratorio2.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.entities.Course;
import it.polito.ai.laboratorio2.entities.Student;
import it.polito.ai.laboratorio2.repositories.CourseRepository;
import it.polito.ai.laboratorio2.repositories.StudentRepository;
import it.polito.ai.laboratorio2.services.exceptions.CourseNotFoundException;
import it.polito.ai.laboratorio2.services.exceptions.StudentNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public Boolean addCourse(CourseDTO course) {
        if(courseRepository.findAll().contains(modelMapper.map(course, Course.class)))
            return false;
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
        if(studentRepository.findAll().contains(modelMapper.map(student, Student.class)))
            return false;
        studentRepository.save(modelMapper.map(student, Student.class));
        return true;
    }

    @Override
    public Optional<StudentDTO> getStudent(String studentId) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getName().equals(studentId))
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
            if(studentRepository.findAll().contains(modelMapper.map(studentDTO, Student.class)))
                booleans.add(false);
            else {
                addStudent(studentDTO);
                booleans.add(true);
            }
        }
        return booleans;
    }

    @Override
    public List<Boolean> enrollAll(List<String> studentIds, String courseName) {
        if(!courseRepository.findById(courseName).isPresent())
            throw new CourseNotFoundException(); //TODO: have to catch here??
        List<Boolean> booleans = new ArrayList<Boolean>();
        for(String studentId : studentIds) {
            try {
                if(!studentRepository.findById(studentId).isPresent())
                    throw new StudentNotFoundException();
                else {
                    courseRepository.getOne(courseName).addStudent(studentRepository.getOne(studentId));
                    booleans.add(true);
                }
            } catch (StudentNotFoundException e) {
                booleans.add(false);
            }
        }
        return booleans;
    }

    @Override
    public List<Boolean> addAndEroll(Reader r, String courseName) {
        /*
        .csv format required:
            id,firstName,name
            s265542,Matteo,Stoisa
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
}

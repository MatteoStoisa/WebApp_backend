package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/courses")
public class CourseController {
    @Autowired
    TeamService teamService;

    @GetMapping({"", "/"})
    List<CourseDTO> all() {
        return teamService.getAllCourses()
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    CourseDTO getOne(@PathVariable("name") String name) {
        Optional<CourseDTO> courseDTO = teamService.getCourse(name);
        if(courseDTO.isPresent()) {
            return ModelHelper.enrich(courseDTO.get());
        }
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
    }

    @GetMapping("/{name}/enrolled")
    List<StudentDTO> enrolledStudents(@PathVariable("name") String name) {
        return teamService.getEnrolledStudents(name)
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @PostMapping({"", "/"})
    CourseDTO addCourse(@RequestBody CourseDTO courseDTO) {
        if(teamService.addCourse(courseDTO))
            return ModelHelper.enrich(courseDTO);
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, courseDTO.getName());
    }

    @PostMapping("/{name}/enrollOne")
    CourseDTO enrollOne(@RequestBody String studentId, @PathVariable("name") String courseName) {
        if(teamService.addStudentToCourse(studentId, courseName))
            return ModelHelper.enrich(teamService.getCourse(courseName).get());
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, studentId);
    }

    @PostMapping("/{name}/enrollMany")
    /*List<boolean>*/ String enrollStudents(@PathVariable("name") String courseName, @RequestParam("file") MultipartFile file) {
    /*if(file.getContentType())*/
        return file.getContentType();
        //TODO: continue here
    }


}

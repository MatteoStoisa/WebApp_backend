package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.services.NotificationService;
import it.polito.ai.laboratorio2.services.TeamService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/courses")
@Log(topic = "CourseController")
public class CourseController {
    @Autowired
    TeamService teamService;
    @Autowired
    NotificationService notificationService;

    @GetMapping({"", "/"})
    public List<CourseDTO> all() {
        return teamService.getAllCourses()
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public CourseDTO getOne(@PathVariable("name") String name) {
        Optional<CourseDTO> courseDTO = teamService.getCourse(name);
        if(courseDTO.isPresent()) {
            return ModelHelper.enrich(courseDTO.get());
        }
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, name);
    }

    @GetMapping("/{name}/enrolled")
    public List<StudentDTO> enrolledStudents(@PathVariable("name") String name) {
        return teamService.getEnrolledStudents(name)
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @PostMapping({"", "/"})
    public CourseDTO addCourse(@RequestBody CourseDTO courseDTO) {
        if(teamService.addCourse(courseDTO))
            return ModelHelper.enrich(courseDTO);
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, courseDTO.getName());
    }

    @PostMapping("/{name}/enrollOne")
    public CourseDTO enrollOne(@RequestBody Map<String, String> input, @PathVariable("name") String courseName) {
        if(teamService.addStudentToCourse(input.get("studentId"), courseName))
            return ModelHelper.enrich(teamService.getCourse(courseName).get());
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, input.get("studentId")+courseName);
    }

    @PostMapping("/{name}/enrollMany")
    public List<Boolean> enrollStudents(@PathVariable("name") String courseName, @RequestParam("file") MultipartFile file){
        if(file == null)
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "no file");
        if(file.getContentType().equals("text/csv")) {
            List<Boolean> booleanList = new ArrayList<>();
            try {
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                booleanList = teamService.addAndEnroll(reader, courseName);
                return booleanList;
            } catch (IOException IOE) {
                booleanList.add(false);
                return booleanList;
            }
        }
        else
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, file.getName());
        //TODO: test better
    }


}

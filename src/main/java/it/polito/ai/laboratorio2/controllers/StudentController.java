package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.dtos.TeamDTO;
import it.polito.ai.laboratorio2.services.TeamService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/students")
@Log(topic = "StudentController")
public class StudentController {
    @Autowired
    TeamService teamService;

    @GetMapping({"", "/"})
    public List<StudentDTO> all() {
        return teamService.getAllStudents()
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StudentDTO getOne(@PathVariable("id") String id) {
        try {
            return ModelHelper.enrich(teamService.getStudent(id).get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"", "/"})
    public StudentDTO addStudent(@RequestBody @Valid StudentDTO studentDTO) {
        if(teamService.addStudent(studentDTO))
            return ModelHelper.enrich(studentDTO);
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    @GetMapping("/{id}/getCourses")
    public List<CourseDTO> getCourses(@PathVariable("id") String id) {
        try {
            return teamService.getCourses(id)
                    .stream()
                    .map(ModelHelper::enrich)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/getTeams")
    public List<TeamDTO> getTeams(@PathVariable("id") String id) {
        try {
                return teamService.getTeamsForStudent(id)
                    .stream()
                    .map(ModelHelper::enrich)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}

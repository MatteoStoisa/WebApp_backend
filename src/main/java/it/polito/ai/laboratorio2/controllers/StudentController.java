package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/students")
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
        Optional<StudentDTO> studentDTO = teamService.getStudent(id);
        if(studentDTO.isPresent()) {
            return ModelHelper.enrich(studentDTO.get());
        }
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, id);
    }

    @PostMapping({"", "/"})
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) {
        if(teamService.addStudent(studentDTO))
            return ModelHelper.enrich(studentDTO);
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, studentDTO.getId());
    }
}

package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.dtos.TeacherDTO;
import it.polito.ai.laboratorio2.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/teachers")
public class TeacherController {
    @Autowired
    TeamService teamService;

    @GetMapping({"", "/"})
    public List<TeacherDTO> all() {
        return teamService.getAllTeachers()
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public TeacherDTO getOne(@PathVariable("email") String email) {
        Optional<TeacherDTO> teacherDTO = teamService.getTeacher(email);
        if(teacherDTO.isPresent()) {
            return ModelHelper.enrich(teacherDTO.get());
        }
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, email);
    }

    @PostMapping({"", "/"})
    public TeacherDTO addTeacher(@RequestBody TeacherDTO teacherDTO) {
        if(teamService.addTeacher(teacherDTO))
            return ModelHelper.enrich(teacherDTO);
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, teacherDTO.getEmail());
    }
}
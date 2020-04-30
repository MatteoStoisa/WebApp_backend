package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class ModelHelper {
    public static CourseDTO enrich(CourseDTO courseDTO) {
        courseDTO.add(WebMvcLinkBuilder.linkTo(CourseController.class).slash(courseDTO.getName()).withSelfRel());
        courseDTO.add(WebMvcLinkBuilder.linkTo(CourseController.class).slash(courseDTO.getName() + "enrolled").withSelfRel());
        return courseDTO;
    }

    public static StudentDTO enrich(StudentDTO studentDTO) {
        studentDTO.add(WebMvcLinkBuilder.linkTo(StudentController.class).slash(studentDTO.getId()).withSelfRel());
        return studentDTO;
    }
}

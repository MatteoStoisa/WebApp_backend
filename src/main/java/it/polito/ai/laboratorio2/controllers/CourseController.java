package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.CourseDTO;
import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.dtos.TeamDTO;
import it.polito.ai.laboratorio2.dtos.TeamProposal;
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
import java.util.*;
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "course "+name+" not found");
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "invalid course");
    }

    @PostMapping("/{name}/enrollOne")
    public CourseDTO enrollOne(@RequestBody Map<String, String> requestBody,
                               @PathVariable("name") String courseName) {
        if(teamService.addStudentToCourse(requestBody.get("studentId"), courseName))
            return ModelHelper.enrich(teamService.getCourse(courseName).get());
        else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "invalid parameters");
    }

    @PostMapping("/{name}/enrollMany")
    public List<Boolean> enrollStudents(@PathVariable("name") String courseName,
                                        @RequestParam("file") MultipartFile file){
        if(file == null)
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "file error");
        if(Objects.equals(file.getContentType(), "text/csv")) {
            List<Boolean> booleanList = new ArrayList<>();
            try {
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                booleanList = teamService.addAndEnroll(reader, courseName);
                return booleanList;
            } catch (IOException c) {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, file.getName()+" file error");
            }
        }
        else
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, file.getName()+" wrong file extension");
        //TODO: improve exception management
    }

    @PostMapping("/{name}/proposeTeam")
    public TeamDTO proposeTeam(@PathVariable("name") String courseName,
                               @RequestBody TeamProposal teamProposal) {
        try {
            TeamDTO teamDTO = teamService.proposeTeam(courseName,teamProposal.getTeamName(), teamProposal.getMemberIds());
            notificationService.notifyTeam(teamDTO, teamProposal.getMemberIds());
            return ModelHelper.enrich(teamDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "invalid team proposal");
        }
    }

    @GetMapping("/{name}/enable")
    public void enableCourse(@PathVariable("name") String courseName) {
        teamService.enableCourse(courseName);
    }

    @GetMapping("/{name}/disable")
    public void disableCourse(@PathVariable("name") String courseName) {
        teamService.disableCourse(courseName);
    }

    @GetMapping("/{name}/getTeams")
    public List<TeamDTO> getTeams(@PathVariable("name") String courseName) {
        return teamService.getTeamForCourse(courseName)
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}/getStudentsInTeams")
    public List<StudentDTO> getStudentsInTeams(@PathVariable("name") String courseName) {
        return teamService.getStudentsInTeams(courseName)
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}/getAvailableStudents")
    public List<StudentDTO> getAvailableStudents(@PathVariable("name") String courseName) {
        return teamService.getAvailableStudents(courseName)
                .stream()
                .map(ModelHelper::enrich)
                .collect(Collectors.toList());
    }
}

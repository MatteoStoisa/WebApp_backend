package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.dtos.StudentDTO;
import it.polito.ai.laboratorio2.repositories.TeamRepository;
import it.polito.ai.laboratorio2.services.TeamService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/teams")
@Log(topic = "TeamController")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/{teamId}/getMembers")
    public List<StudentDTO> getMembers(@PathVariable("teamId") String teamId) {
        try {
            return teamService.getMembers(Long.parseLong(teamId))
                    .stream()
                    .map(ModelHelper::enrich)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, teamId);
        }
    }
}

package it.polito.ai.laboratorio2.services;

import it.polito.ai.laboratorio2.controllers.NotificationController;
import it.polito.ai.laboratorio2.dtos.TeamDTO;
import it.polito.ai.laboratorio2.entities.Token;
import it.polito.ai.laboratorio2.repositories.TeamRepository;
import it.polito.ai.laboratorio2.repositories.TokenRepository;
import it.polito.ai.laboratorio2.services.exceptions.tokenException.TokenUnknownException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Log(topic = "NotificationServiceImpl")
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    TeamService teamService;
    @Autowired
    JavaMailSender emailSender;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    TeamRepository teamRepository;

    @Override
    public void sendMessage(String address, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
        log.info("notification mail sent to '" + address + "'");
    }

    @Override
    public boolean confirm(String token) {
        if(!tokenRepository.findById(token).isPresent())
            throw new TokenUnknownException();
        if(tokenRepository.getOne(token).getExpiryDate().after(new Timestamp(new Date().getTime()))) {
            Long teamId = tokenRepository.getOne(token).getTeamId();
            tokenRepository.delete(tokenRepository.getOne(token));
            if(tokenRepository.findAllByTeamId(teamId).size() == 0) {
                teamService.setTeamStatus(teamId, 1);
            }
            return true;
        }
        else {
            reject(token);
            return false;
        }
    }

    @Override
    public boolean reject(String token) {
        if(!tokenRepository.findById(token).isPresent())
            throw new TokenUnknownException();
        Long teamId = tokenRepository.getOne(token).getTeamId();
        tokenRepository.findAllByTeamId(teamId)
                .forEach(t -> tokenRepository.delete(t));
        teamService.evictTeam(teamId);
        return true;
    }

    @Override
    public void notifyTeam(TeamDTO dto, List<String> memberIds) {
        for(String member : memberIds) {
            Token token = new Token();
            token.setId(UUID.randomUUID().toString());
            token.setTeamId(dto.getId());
            token.setExpiryDate(new Timestamp(new Date().getTime() + 3600*1000));
            tokenRepository.save(token);
            //TODO: no id rule -> mail may be wrong
            String address = "s" + member + "@studenti.polito.it";
            String body = "\nHello " + member + "! \n\n" +
                    "Please CONFIRM the Team Invitation: \n" +
                    WebMvcLinkBuilder.linkTo(NotificationController.class).slash("/confirm/"+token.getId()) + " \n" +
                    "or REJECT it: \n" +
                    WebMvcLinkBuilder.linkTo(NotificationController.class).slash("/reject/"+token.getId());
            //TODO: test mail hardcoded here
            sendMessage(/*address*/ "derrick1rose95@gmail.com", "Confirm Team Invitation", body);
        }
    }
}

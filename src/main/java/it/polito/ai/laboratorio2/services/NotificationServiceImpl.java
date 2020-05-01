package it.polito.ai.laboratorio2.services;

import it.polito.ai.laboratorio2.dtos.TeamDTO;
import it.polito.ai.laboratorio2.repositories.TokenRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    @Override
    public void sendMessage(String address, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
        log.info("mail successfully sent to '" + address + "'");
        //notificationService.sendMessage("mail", "subject", "body");
    }

    @Override
    public boolean confirm(String token) {
        if(tokenRepository.findById(token).isPresent() && tokenRepository.getOne(token).getExpiryDate().before(new Timestamp(new Date().getTime()))) {
            Long teamId = tokenRepository.getOne(token).getTeamId();
            tokenRepository.delete(tokenRepository.getOne(token));
            if(tokenRepository.findAllByTeamId(teamId).size() == 0) {
                teamService.setTeamStatus(teamId, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean reject(String token) {
        if(tokenRepository.findById(token).isPresent()) {
            Long teamId = tokenRepository.getOne(token).getTeamId();
            tokenRepository.findAllByTeamId(teamId)
                    .forEach(t -> teamService.evictTeam(t.getTeamId()));
        }
        return true;
    }

    @Override
    public void notifyTeam(TeamDTO dto, List<String> memberIds) {
        //TODO: continue here
    }
}

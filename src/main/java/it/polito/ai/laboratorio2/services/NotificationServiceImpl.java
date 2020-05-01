package it.polito.ai.laboratorio2.services;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Log(topic = "NotificationServiceImpl")
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendMessage(String address, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
        log.info("mail successfully sent to '" + address + "'");
    }
    //notificationService.sendMessage("mail", "subject", "body");
}

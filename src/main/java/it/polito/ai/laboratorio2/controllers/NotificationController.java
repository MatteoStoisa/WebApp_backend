package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.repositories.TokenRepository;
import it.polito.ai.laboratorio2.services.NotificationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/API/notification")
@Log(topic = "NotificationController")
public class NotificationController {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/confirm/{tokenId}")
    public String confirmToken(@PathVariable("tokenId") String tokenId) {
        try {
            if(notificationService.confirm(tokenId))
                return "teamRequestAccepted";
            return "teamRequestRejected";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reject/{tokenId}")
    public String rejectToken(@PathVariable("tokenId") String tokenId) {
        try {
            notificationService.reject(tokenId);
            return "teamRequestRejected";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

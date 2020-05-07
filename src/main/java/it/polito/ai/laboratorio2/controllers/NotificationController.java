package it.polito.ai.laboratorio2.controllers;

import it.polito.ai.laboratorio2.repositories.TokenRepository;
import it.polito.ai.laboratorio2.services.NotificationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        if(notificationService.confirm(tokenId))
            return "teamReqtestAccepted";
        //TODO: no difference between intermediate or final accept
        return "teamRequestRejected";
    }

    @GetMapping("/reject/{tokenId}")
    public String rejectToken(@PathVariable("tokenId") String tokenId) {
        notificationService.reject(tokenId);
        return "teamRequestRejected";
    }
}

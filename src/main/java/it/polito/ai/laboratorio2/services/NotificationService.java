package it.polito.ai.laboratorio2.services;

import it.polito.ai.laboratorio2.dtos.TeamDTO;

import java.util.List;

public interface NotificationService {
    void sendMessage(String address, String subject, String Body);
    boolean confirm(String token);
    boolean reject(String token);
    void notifyTeam(TeamDTO dto, List<String> memberIds);
}

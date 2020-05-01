package it.polito.ai.laboratorio2.services;

public interface NotificationService {
    void sendMessage(String address, String subject, String Body);
}

package it.polito.ai.laboratorio2.repositories;

import it.polito.ai.laboratorio2.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Timestamp;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {
    //TODO: query!
    List<Token> findAllByExpiryBefore(Timestamp t);
    List<Token>findAllByTeamId(Long teamId);
}

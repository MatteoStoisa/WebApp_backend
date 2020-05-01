package it.polito.ai.laboratorio2.repositories;

import it.polito.ai.laboratorio2.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    //TODO: test < timestamp
    @Query("SELECT t FROM Token t WHERE t.expiryDate<:t")
    List<Token> findAllByExpiryBefore(Timestamp t);
    @Query("SELECT t FROM Token t WHERE t.teamId=:teamId")
    List<Token> findAllByTeamId(Long teamId);
}

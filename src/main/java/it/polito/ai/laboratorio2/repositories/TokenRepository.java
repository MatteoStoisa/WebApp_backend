package it.polito.ai.laboratorio2.repositories;

import it.polito.ai.laboratorio2.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    @Query("SELECT t FROM Token t WHERE t.expiryDate<:timestamp")
    List<Token> findAllByExpiryBefore(Timestamp timestamp);
    @Query("SELECT t FROM Token t WHERE t.teamId=:teamId")
    List<Token> findAllByTeamId(Long teamId);
}


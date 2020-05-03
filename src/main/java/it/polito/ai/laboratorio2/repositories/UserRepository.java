package it.polito.ai.laboratorio2.repositories;

import it.polito.ai.laboratorio2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
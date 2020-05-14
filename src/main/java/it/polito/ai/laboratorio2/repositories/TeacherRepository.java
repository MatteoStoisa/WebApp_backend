package it.polito.ai.laboratorio2.repositories;

import it.polito.ai.laboratorio2.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
}

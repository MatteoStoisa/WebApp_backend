package it.polito.ai.laboratorio2.repositories;

import it.polito.ai.laboratorio2.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}

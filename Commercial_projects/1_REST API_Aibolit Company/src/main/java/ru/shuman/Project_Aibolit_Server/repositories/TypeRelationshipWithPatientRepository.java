package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.TypeRelationshipWithPatient;

import java.util.Optional;

@Repository
public interface TypeRelationshipWithPatientRepository extends JpaRepository<TypeRelationshipWithPatient, Integer> {
    Optional<TypeRelationshipWithPatient> findByName(String name);
}

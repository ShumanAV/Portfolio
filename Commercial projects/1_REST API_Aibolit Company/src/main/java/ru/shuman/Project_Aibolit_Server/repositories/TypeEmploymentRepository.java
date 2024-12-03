package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.TypeEmployment;

import java.util.Optional;

@Repository
public interface TypeEmploymentRepository extends JpaRepository<TypeEmployment, Integer> {
    Optional<TypeEmployment> findByName(String name);
}

package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Education;

import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<Education> findByName(String name);
}

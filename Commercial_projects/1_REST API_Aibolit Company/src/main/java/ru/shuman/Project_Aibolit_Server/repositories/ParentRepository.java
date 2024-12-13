package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
    Optional<Parent> findByPhone(String phone);
    Optional<Parent> findByEmail(String email);
    Optional<Parent> findByPolicy(String policy);
    Optional<Parent> findBySnils(String snils);
    Optional<Parent> findByInn(String inn);
}

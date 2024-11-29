package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.models.Doctor;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByDoctor(Doctor doctor);
}

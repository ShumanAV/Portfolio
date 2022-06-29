package com.example.Project_2_Library_Spring.Boot_Lesson_74.repositories;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByFullName(String fullName);

    Optional<Person> findByUsername(String username);

    List<Person> findByRole(String role);
}

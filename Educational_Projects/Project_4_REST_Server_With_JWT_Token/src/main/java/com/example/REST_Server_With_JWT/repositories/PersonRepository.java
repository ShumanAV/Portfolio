package com.example.REST_Server_With_JWT.repositories;

import com.example.REST_Server_With_JWT.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Репозиторий для работы с таблицей Person с БД
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}

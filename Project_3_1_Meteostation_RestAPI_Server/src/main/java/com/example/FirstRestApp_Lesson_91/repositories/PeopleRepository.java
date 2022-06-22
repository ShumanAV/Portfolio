package com.example.FirstRestApp_Lesson_91.repositories;

import com.example.FirstRestApp_Lesson_91.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository параметризуем классом, который является сущностью и тип первичного ключа
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}

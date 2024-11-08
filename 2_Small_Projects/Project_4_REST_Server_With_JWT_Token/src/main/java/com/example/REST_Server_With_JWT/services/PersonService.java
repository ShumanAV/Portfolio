package com.example.REST_Server_With_JWT.services;

import com.example.REST_Server_With_JWT.models.Person;
import com.example.REST_Server_With_JWT.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

// Сервис для работы с таблицей Person с БД
@Service
public class PersonService {

    private final PersonRepository personRepository;

    // внедрение зависимостей через конструктор
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // данный метод осуществляет поиск человека по имени в БД
    public Optional<Person> findByName(String name) {
        return personRepository.findByName(name);
    }

}

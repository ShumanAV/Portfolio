package com.example.Project_2_Library_Spring.Boot_Lesson_74.services;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final PersonRepository personRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public AdminService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /*
    Метод устанавливает выбранную роль у выбранного пользователя
     */
    @Transactional
    public void setRole(Person selectedPerson, String role) {
        Optional<Person> person = personRepository.findById(selectedPerson.getId());

        if (person.isPresent()) {
            person.get().setRole(role);
            personRepository.save(person.get());
        }
    }
}

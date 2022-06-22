package com.example.FirstRestApp_Lesson_91.services;

import com.example.FirstRestApp_Lesson_91.models.Person;
import com.example.FirstRestApp_Lesson_91.repositories.PeopleRepository;
import com.example.FirstRestApp_Lesson_91.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

     public Person findOne(int id) {
         return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
     }

     @Transactional
     public void save(Person person) {
        enrichPerson(person);       // дополним данными
        peopleRepository.save(person);
     }

    // метод для обогащения объекта Person данными, которые формируются на сервере
    // вынесем его из контроллера в сервис, чтобы уменьшить кол-во логики в контроллере
    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");  // пока кладем строку с именем, в реальном приложении определяли бы и клали имя пользователя, который создал
    }

}

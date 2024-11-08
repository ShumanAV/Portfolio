package com.example.Project_2_Library_Spring.Boot_Lesson_74.services;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Book;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // поиск всех людей
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    // поиск человека по id
    public Person findById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    // сохранение нового человека
    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    // изменение существующего человека
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    // удаление человека
    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    // поиск книг по id человека, подсчет просрочки взятия книги, с учетом срока в 10 дней, установка просрочено/непросрочено в книге
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            List<Book> books = person.get().getBooks();
            for (Book book : books) {
                Date bookDateOfTaken = book.getDateOfTaken();
                Date nowDate = new Date();
                Long tenDaysInMs = 10l * 24 * 60 * 60 * 1000;
                book.setExpired(Math.abs(nowDate.getTime() - bookDateOfTaken.getTime()) > tenDaysInMs);
            }
            return books;
        } else {
            return Collections.emptyList();
        }
    }

    // поиск человека по логину
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    // поиск человека по роли
    public List<Person> findByRole(String role) {
        return personRepository.findByRole(role);
    }

}

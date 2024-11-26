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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /*
    Метод формирует и возвращает список всех людей
     */
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    /*
     Метод ищет человека по id и возвращает его
     */
    public Person findById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    /*
     Метод сохраняет нового человека в БД
     */
    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    /*
    Метод принимает измененного человека и сохраняет его в БД
     */
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    /*
     Метод удаляет человека по его id
     */
    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    /*
     Метод ищет книги по id человека, подсчет просрочки взятия книги, с учетом срока в 10 дней,
     установка флага просрочено/непросрочено в книге
     */
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

    /*
     Метод ищет человека по логину
     */
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    /*
     Метод ищет людей по роли и возвращает данный список пользователей
     */
    public List<Person> findByRole(String role) {
        return personRepository.findByRole(role);
    }

}

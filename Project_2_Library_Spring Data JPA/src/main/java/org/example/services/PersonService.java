package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        Person person = personRepository.findById(id).orElse(null);
        List<Book> books = person.getBooks();
        for (Book book: books) {
            Date bookDateOfTaken = book.getDateOfTaken();
            Date nowDate = new Date();
            Long tenDaysInMs = 10l * 24 * 60 * 60 * 1000;
            book.setOverdueBook((nowDate.getTime() - bookDateOfTaken.getTime()) > tenDaysInMs);
        }
        return books;
    }
}

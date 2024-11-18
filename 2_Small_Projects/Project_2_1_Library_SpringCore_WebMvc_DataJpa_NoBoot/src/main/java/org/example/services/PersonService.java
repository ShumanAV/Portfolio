package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class PersonService {
    private final PeopleRepository peopleRepository;
    private static final int TEN_DAYS = 10;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /*
    Метод формирует и возвращает список всех читателей из БД
     */
    public List<Person> index() {
        return peopleRepository.findAll();
    }

    /*
    Метод ищет человека по его id и возвращает его
     */
    public Optional<Person> show(int personId) {
        return peopleRepository.findById(personId);
    }

    /*
    Метод ищет человека по его имени и возвращает его
     */
    public Optional<Person> show(String name) {
        return peopleRepository.findByName(name);
    }

    /*
    Метод сохраняет в БД нового человека
     */
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    /*
    Метод сохраняет измененные данные человека, человек ищется по его id, на вход также принимает измененного человека
     */
    @Transactional
    public void update(int personId, Person updatedPerson) {
        updatedPerson.setPersonId(personId);
        peopleRepository.save(updatedPerson);
    }

    /*
    Метод удаляет из БД человека по его id
     */
    @Transactional
    public void delete(int personId) {
        peopleRepository.deleteById(personId);
    }

    /*
    Метод возвращает список книг, которые закреплены за человеком, при их наличии и возвращает данный список
     */
    public List<Book> getBooks(int personId) {
        Optional<Person> reader = peopleRepository.findById(personId);
        return expiredBooks(reader.get().getBooks());
    }

    /*
    Метод принимает список книг и проверяет дату взятия читателем каждой книги, если прошло уже более 10 дней
    с момента взятия, то книга считается просроченной, выставляется флаг просроченности
     */
    private List<Book> expiredBooks(List<Book> books) {
        for (Book book: books){
            if (Math.abs((new Date().getTime() - book.getTakenAt().getTime())/1000/60/60/24) > TEN_DAYS) {
                book.setExpired(true);
            }
        }
        return books;
    }
}

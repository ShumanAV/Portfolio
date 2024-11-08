package org.example.DAO;

import org.example.Models.Book;
import org.example.Models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    Метод формирует и возвращает список всех читателей из БД
     */
    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    /*
    Метод ищет человека по его id и возвращает его
     */
    public Optional<Person> show(int personId) {
        return jdbcTemplate.query("select * from person where person_id=?", new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    /*
    Метод ищет человека по его имени и возвращает его
     */
    public Optional<Person> show(String name) {
        return jdbcTemplate.query("select * from person where name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    /*
    Метод сохраняет в БД нового человека
     */
    public void save(Person person) {
        jdbcTemplate.update("insert into person (name, year_of_birth) VALUES (?, ?)",
                person.getName(), person.getYearOfBirth());
    }

    /*
    Метод сохраняет измененные данные человека, человек ищется по его id, на вход также принимает измененного человека
     */
    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?, year_of_birth=? where person_id=?",
                updatedPerson.getName(), updatedPerson.getYearOfBirth(), personId);
    }

    /*
    Метод удаляет из БД человека по его id
     */
    public void delete(int personId) {
        jdbcTemplate.update("delete from person where person_id=?", personId);
    }

    /*
    Метод возвращает список книг которые закреплены за человеком при их наличии и возвращает данный список
     */
    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("select * from book where person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}

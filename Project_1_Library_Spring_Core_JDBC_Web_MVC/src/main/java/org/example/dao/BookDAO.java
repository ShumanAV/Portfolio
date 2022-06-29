package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> show(String title, String author, int yearOfWriting) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE title=? and author=? and year_of_writing=?", new Object[]{title, author, yearOfWriting},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void add(Book book) {
        jdbcTemplate.update("INSERT INTO Book (title, author, year_of_writing) values (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYearOfWriting());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year_of_writing=? WHERE book_id=?",
                book.getTitle(), book.getAuthor(), book.getYearOfWriting(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    // Получаем человека, которому принадлежит указанная книга с указанным id
    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Person INNER JOIN Book ON Person.person_id = Book.person_id WHERE Book.book_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    // Освобождаем книгу по id книги, когда человек возвращает ее
    public void release(int id) {
        jdbcTemplate.update("Update Book Set person_id = NULL WHERE book_id = ?", id);
    }

    // Назначаем книге c id человека selectedPerson
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("Update Book Set person_id = ? WHERE book_id = ?",
                selectedPerson.getPerson_id(), id);
    }
}

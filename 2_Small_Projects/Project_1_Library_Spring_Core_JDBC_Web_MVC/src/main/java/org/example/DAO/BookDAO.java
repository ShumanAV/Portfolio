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
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    Метод формирует список всех книг из БД и возвращает его
     */
    public List<Book> index() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    /*
    Метод ищет в БД книгу по ее id и возвращает ее
     */
    public Optional<Book> show(int bookId) {
        return jdbcTemplate.query("select * from book where book_id=?", new Object[]{bookId},
                        new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    /*
    Метод ищет в БД книгу по ее названию и возвращает ее
     */
    public Optional<Book> show(String title) {
        return jdbcTemplate.query("select * from book where title=?", new Object[]{title},
                        new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    /*
    Метод сохраняет новую книгу в БД
     */
    public void save(Book book) {
        jdbcTemplate.update("insert into book (person_id, title, author, year_of_writing) VALUES (?, ?, ?, ?)",
                null, book.getTitle(), book.getAuthor(), book.getYearOfWriting());
    }

    /*
    Метод изменяет существующую книгу, книгу ищет по ее id, также на вход принимает измененную книгу
     */
    public void update(int bookId, Book updatedbook) {
        jdbcTemplate.update("update book set title=?, author=?, year_of_writing=? where book_id=?",
                updatedbook.getTitle(), updatedbook.getAuthor(), updatedbook.getYearOfWriting(), bookId);
    }

    /*
    Метод удаляет книгу по ее id
     */
    public void delete(int bookId) {
        jdbcTemplate.update("delete from book where book_id=?", bookId);
    }

    /*
    Метод закрепляет книгу за выбранным читателем, т.е. находит книгу по ее id и устанавливает в поле id читателя
     */
    public void assign(int bookId, int personId) {
        jdbcTemplate.update("update book set person_id=? where book_id=?", personId, bookId);
    }

    /*
    Метод освобождает книгу от читателя, в поле читателя устанавливает null
     */
    public void release(int bookId) {
        jdbcTemplate.update("update book set person_id=NULL where book_id=?", bookId);
    }

    // Метод возвращает человека, который взял определенную книгу по ее id
    public Optional<Person> getBookReader(int bookId) {
        return jdbcTemplate.query("select * from person where person_id = (select book.person_id from book where book_id=?)",
                new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}

package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    /*
    Внедрение зависимостей через конструктор
    */
    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    /*
    Метод формирует список всех книг из БД и возвращает его
     */
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    /*
    Метод формирует список всех книг из БД учитывая сортировку и возвращает его
    */
    public List<Book> findAll(boolean sortByYear) {
        return booksRepository.findAll(Sort.by("yearOfWriting"));
    }

    /*
    Метод формирует список всех книг из БД учитывая пагинацию и возвращает его
    */
    public List<Book> findAll(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    /*
    Метод формирует список всех книг из БД учитывая пагинацию и сортировку и возвращает его
    */
    public List<Book> findAll(int page, int booksPerPage, boolean sortByYear) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfWriting"))).getContent();
    }

    /*
    Метод ищет в БД книгу по ее id и возвращает ее
     */
    public Optional<Book> show(int bookId) {
        return booksRepository.findById(bookId);
    }

    /*
    Метод ищет в БД книгу по ее названию и возвращает ее
     */
    public Optional<Book> show(String title) {
        return booksRepository.findByTitle(title);
    }

    /*
    Метод сохраняет новую книгу в БД
     */
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    /*
    Метод изменяет существующую книгу, книгу ищет по ее id, также на вход принимает измененную книгу,
    для сохранения сущности в таблицу не нужно использовать метод save(), т.к. сущность уже находится в Persistence Context
     */
    @Transactional
    public void update(int bookId, Book updatedbook) {
        Book bookToBeUpdated = booksRepository.findById(bookId).get();

        bookToBeUpdated.setTitle(updatedbook.getTitle());
        bookToBeUpdated.setAuthor(updatedbook.getAuthor());
        bookToBeUpdated.setYearOfWriting(updatedbook.getYearOfWriting());
    }

    /*
    Метод удаляет книгу по ее id
     */
    @Transactional
    public void delete(int bookId) {
        booksRepository.deleteById(bookId);
    }

    /*
    Метод закрепляет книгу за выбранным читателем, т.е. находит книгу по ее id и устанавливает в поле id читателя,
    кроме этого в поле даты и времени взятия книги устанавливается текущее дата и время
     */
    @Transactional
    public void assign(int bookId, int personId) {
        Person newReader = peopleRepository.findById(personId).get();
        Book book = booksRepository.findById(bookId).get();

        book.setTakenAt(new Date());
        book.setReader(newReader);

        //для кэша
        newReader.getBooks().add(book);
    }

    /*
    Метод освобождает книгу от читателя, в поле читателя устанавливает null, также обнуляет дату и время взятия книги
    и устанавливает null в данное поле, кроме этого сбрасывается флаг просроченности книги в false
     */
    @Transactional
    public void release(int bookId) {
        Book book = booksRepository.findById(bookId).get();

        //для кэша
        book.getReader().getBooks().remove(book);

        book.setTakenAt(null);
        book.setExpired(false);
        book.setReader(null);
    }

    // Метод возвращает человека, который взял определенную книгу по ее id
    public Optional<Person> getBookReader(int bookId) {
        return booksRepository.findById(bookId).map(Book::getReader);
    }

    /*
    Метод возвращает список книг, которые начинаются со строки
     */
    public List<Book> searchByTitle(String startingWith) {
        return booksRepository.findByTitleIgnoreCaseStartingWith(startingWith);
    }
}

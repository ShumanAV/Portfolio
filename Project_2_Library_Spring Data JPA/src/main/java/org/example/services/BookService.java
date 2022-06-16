package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(Boolean sortByYear) {
        return bookRepository.findAll(Sort.by(returnSortedField(sortByYear)));
    }

    public List<Book> findAll(int pageNumber, int booksPerPage, Boolean sortByYear) {
        return bookRepository.findAll(PageRequest.of(pageNumber, booksPerPage, Sort.by(returnSortedField(sortByYear)))).getContent();
    }

    // внутренний метод для определения поля для сортировки книг
    private String returnSortedField(Boolean sortByYear) {
        String sortedField;
        if (sortByYear == null || !sortByYear) {
            sortedField = "bookId";
        } else {
            sortedField = "yearOfWriting";
        }
        return sortedField;
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    // Получаем человека, которому принадлежит указанная книга с указанным id
    public Optional<Person> getBookOwner(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    // Освобождаем книгу по id книги, когда человек возвращает ее
    public void release(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        book.setOwner(null);
    }

    @Transactional
    // Назначаем книге c id человека selectedPerson
    public void assign(int id, Person selectedPerson) {
        Book book = bookRepository.findById(id).orElse(null);
        book.setOwner(selectedPerson);
        book.setDateOfTaken(new Date());
    }

    public List<Book> findByTitleStartingWith(String startingWith) {
        return bookRepository.findByTitleStartingWith(startingWith);
    }
}

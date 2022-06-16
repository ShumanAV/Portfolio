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

    public List<Book> findAllWithPagination(int pageNumber, int booksPerPage, Boolean sortByYear) {
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
        Book bookToBeUpdated = bookRepository.findById(id).get();

        //добавляем по сути новую книгу, которая не находится в Persistent Context, поэтому нужен save()
        updatedBook.setBookId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());   // у редактируемой книги поле owner null, поэтому его заполняем, чтобы связь не терялась

        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    // Получаем человека, которому принадлежит указанная книга с указанным id
    public Optional<Person> getBookOwner(int id) {
        // здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
        Book book = bookRepository.findById(id).orElse(null);
        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    // Освобождаем книгу по id книги (убираем owner'а и дату взятия книги), когда человек возвращает ее
    public void release(int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setDateOfTaken(null);
                    book.setOwner(null);
                }
        );

    }

    @Transactional
    // Назначаем книге c id человека selectedPerson (устанавливаем owner'а и дату взятия книги - текущую дату)
    public void assign(int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setDateOfTaken(new Date());
                }
        );
    }

    public List<Book> findByTitleStartingWith(String startingWith) {
        return bookRepository.findByTitleStartingWith(startingWith);
    }
}

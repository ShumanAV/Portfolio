package com.example.Project_2_Library_Spring.Boot_Lesson_74.services;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Book;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.repositories.BookRepository;
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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /*
     Метод формирует и возвращает список всех книг
     */
    public List<Book> findAll(Boolean sortByYear) {
        return bookRepository.findAll(Sort.by(returnSortedField(sortByYear)));
    }

    /*
     Метод формирует и возвращает список всех книг с учетом пагинации и сортировки
     */
    public List<Book> findAllWithPagination(int pageNumber, int booksPerPage, Boolean sortByYear) {
        return bookRepository.findAll(PageRequest.of(pageNumber, booksPerPage, Sort.by(returnSortedField(sortByYear)))).getContent();
    }

    /*
     Внутренний метод для определения поля для сортировки книг, если не задана сортировка по году написания книги,
     значит сортируем по bookId стандартно
     */
    private String returnSortedField(Boolean sortByYear) {
        String sortedField;
        if (sortByYear == null || !sortByYear) {
            sortedField = "bookId";
        } else {
            sortedField = "yearOfWriting";
        }
        return sortedField;
    }

    /*
     Метод ищет книгу по ее id и возвращает ее
     */
    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    /*
     Метод сохраняет новую книгу в БД
     */
    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    /*
     Метод принимает измененную книгу и ее id, изменяет книгу и сохраняет ее в БД
     */
    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = bookRepository.findById(id).get();

        //добавляем по сути новую книгу, которая не находится в Persistent Context, поэтому нужен save()
        updatedBook.setBookId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        bookRepository.save(updatedBook);
    }

    /*
     Метод удаляет книгу по ее id
     */
    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    /*
     Метод получает человека, которому принадлежит указанная книга с указанным id
     */
    public Optional<Person> getBookOwner(int id) {
        // здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
        Book book = bookRepository.findById(id).orElse(null);
        return Optional.ofNullable(book.getOwner());
    }

    /*
     Метод освобождает книгу по id книги (убираем старого владельца owner'а и дату взятия книги),
     когда человек возвращает ее
     */
    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setDateOfTaken(null);
                    book.setOwner(null);
                }
        );
    }

    /*
     Метод назначает книге c id, человека selectedPerson (устанавливаем нового владельца owner'а и дату взятия книги
     - текущую дату)
     */
    @Transactional
    public void assign(int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setDateOfTaken(new Date());
                }
        );
    }

    /*
     Метод поиска книг по первым буквам
     */
    public List<Book> findByTitleStartingWith(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }

    /*
     Метод поиска книги по названию и автору
     */
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }
}

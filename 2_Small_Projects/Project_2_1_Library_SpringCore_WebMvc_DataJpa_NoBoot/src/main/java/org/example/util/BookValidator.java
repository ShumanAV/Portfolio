package org.example.util;

import org.example.models.Book;
import org.example.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    /*
    В данном валидаторе проверяем на уникальность название книги, в случае если книга с таким названием и отличающимся id
    уже есть в БД, записываем ошибку для данного поля
     */
    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        Optional<Book> bookFromDB = bookService.show(book.getTitle());
        if (bookFromDB.isPresent() && book.getBookId() != bookFromDB.get().getBookId()) {
            errors.rejectValue("title", "", "Книга с таким названием уже есть");
        }
    }
}

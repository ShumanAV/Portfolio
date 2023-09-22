package org.example.util;

import org.example.models.Book;
import org.example.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        // поиск книги с таким же названием
        if (bookService.findByTitle(book.getTitle()).isPresent()) {
            errors.rejectValue("title", "", "Книга с таким названием уже существует");
        }
    }
}

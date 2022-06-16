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

//        if (bookService.show(book.getTitle(), book.getAuthor(), book.getYear_of_writing()).isPresent()) {
//            errors.rejectValue("title", "", "Такая книга с таким автором и годом написания уже существует");
//        }
    }
}

package com.example.Project_2_Library_Spring.Boot_Lesson_74.util;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Book;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.BookService;
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
        if (bookService.findByTitleAndAuthor(book.getTitle(), book.getAuthor()).isPresent()) {
            errors.rejectValue("title", "", "Книга с таким названием и автором уже существует");
        }
    }
}

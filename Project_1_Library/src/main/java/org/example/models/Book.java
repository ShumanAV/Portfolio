package org.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int book_id;

    // вся ниже аннотация по валидации данных импортирована из зависимости org.hibernate.validator
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 200, message = "Title should be between 2 and 200 characters")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    private String author;

    @Min(value = 0, message = "Year of writing should be greater than 0")
    private int year_of_writing;

    public Book(int book_id, String title, String author, int year_of_writing) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.year_of_writing = year_of_writing;
    }

    public Book() {

    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear_of_writing() {
        return year_of_writing;
    }

    public void setYear_of_writing(int year_of_writing) {
        this.year_of_writing = year_of_writing;
    }
}

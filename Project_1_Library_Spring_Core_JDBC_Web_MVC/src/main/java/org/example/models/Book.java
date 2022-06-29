package org.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int bookId;

    // вся ниже аннотация по валидации данных импортирована из зависимости org.hibernate.validator
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 200, message = "Title should be between 2 and 200 characters")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    private String author;

    @Min(value = 0, message = "Year of writing should be greater than 0")
    private int yearOfWriting;

    public Book(int bookId, String title, String author, int yearOfWriting) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfWriting = yearOfWriting;
    }

    // Пустой конструктор нужен для спринга (тот же @ModelAttribute создает объект с помощью пустого конструктора и с помощью
    // сеттеров помещает в него значения
    public Book() {

    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public int getYearOfWriting() {
        return yearOfWriting;
    }

    public void setYearOfWriting(int yearOfWriting) {
        this.yearOfWriting = yearOfWriting;
    }
}

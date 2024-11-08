package org.example.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*
Модель Книги
 */
public class Book {
    private int bookId;
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    private String title;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    private String author;
    @Min(value = 0, message = "Year of writing should be greater than 0")
    private int yearOfWriting;

    /*
    Пустой конструктор нужен для Spring
    */
    public Book() {
    }

    public Book(int bookId, String title, String author, int yearOfWriting) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfWriting = yearOfWriting;
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

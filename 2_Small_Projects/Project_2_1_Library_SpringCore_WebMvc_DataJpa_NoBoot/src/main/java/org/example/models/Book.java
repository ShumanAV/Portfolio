package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

/*
Модель Книги
 */
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    @Column(name = "author")
    private String author;
    @Min(value = 0, message = "Year of writing should be greater than 0")
    @Column(name = "year_of_writing")
    private int yearOfWriting;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "taken_at")
    private Date takenAt;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person reader;
    @Transient
    private boolean expired;

    /*
    Пустой конструктор нужен для Spring
    */
    public Book() {
    }

    public Book(String title, String author, int yearOfWriting) {
        this.title = title;
        this.author = author;
        this.yearOfWriting = yearOfWriting;
    }


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfWriting=" + yearOfWriting +
                '}';
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public Person getReader() {
        return reader;
    }

    public void setReader(Person reader) {
        this.reader = reader;
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

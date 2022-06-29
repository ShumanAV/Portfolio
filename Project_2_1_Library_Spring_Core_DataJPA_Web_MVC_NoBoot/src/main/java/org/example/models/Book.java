package org.example.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    // вся ниже аннотация по валидации данных импортирована из зависимости org.hibernate.validator
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 200, message = "Название должно содержать количество символов от 2 до 200")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Поле Автор не должно быть пустым")
    @Size(min = 2, max = 100, message = "Поле Автор должно содержать количество символов от 2 до 100")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Год написания книги должен быть больше 0")
    @Column(name = "year_of_writing")
    private int yearOfWriting;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_taken")
    private Date dateOfTaken;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    // Hibernate не будет замечать этого поля
    @Transient
    private Boolean expired;

    public Book(String title, String author, int yearOfWriting) {
        this.title = title;
        this.author = author;
        this.yearOfWriting = yearOfWriting;
    }

    // Пустой конструктор нужен для спринга (тот же @ModelAttribute создает объект с помощью пустого конструктора и с помощью
    // сеттеров помещает в него значения
    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfWriting=" + yearOfWriting +
                '}';
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Date getDateOfTaken() {
        return dateOfTaken;
    }

    public void setDateOfTaken(Date dateOfTaken) {
        this.dateOfTaken = dateOfTaken;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
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

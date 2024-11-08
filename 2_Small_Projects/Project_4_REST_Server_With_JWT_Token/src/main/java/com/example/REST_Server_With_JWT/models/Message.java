package com.example.REST_Server_With_JWT.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

// Модель для работы с таблицей Message с БД
@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Сообщение не может быть пустым")
    @Column(name = "text_of_message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person name;

    public Message(@NotEmpty(message = "Сообщение не может быть пустым") String message) {
        this.message = message;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getName() {
        return name;
    }

    public void setName(Person name) {
        this.name = name;
    }
}

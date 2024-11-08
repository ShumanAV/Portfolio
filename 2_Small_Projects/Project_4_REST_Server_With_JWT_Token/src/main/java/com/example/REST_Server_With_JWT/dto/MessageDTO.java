package com.example.REST_Server_With_JWT.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// Класс - DTO для приема/отправки сообщений
public class MessageDTO {

    @NotNull
    @NotEmpty(message = "Имя не может быть пустым")
    private String name;

    @NotNull
    @NotEmpty(message = "Сообщение не может быть пустым")
    private String message;

    public MessageDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

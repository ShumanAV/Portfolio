package com.example.REST_Server_With_JWT.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// Класс - DTO для приема/отправки пользователей
public class PersonDTO {

    @NotNull
    @NotEmpty(message = "Имя не может быть пустым")
    private String name;

    @NotNull
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    public PersonDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package org.example.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*
Модель Человека
 */
public class Person {
    private int personId;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;
    @Min(value = 1900, message = "Year of birth should be greater than 1900")
    private int yearOfBirth;

    /*
    Пустой конструктор нужен для Spring
     */
    public Person() {
    }

    public Person(int personId, String name, int yearOfBirth) {
        this.personId = personId;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

}

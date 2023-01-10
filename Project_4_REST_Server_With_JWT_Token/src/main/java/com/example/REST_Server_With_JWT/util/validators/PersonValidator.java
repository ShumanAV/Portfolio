package com.example.REST_Server_With_JWT.util.validators;

import com.example.REST_Server_With_JWT.models.Person;
import com.example.REST_Server_With_JWT.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

// данный класс предназначен для валидации людей, а именно поля name и password
@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    // в данном методе валидируем поле name и password, если такого человека в БД не существует и если пароли не совпадают, выдаем ошибку
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> foundPerson = personService.findByName(person.getName());

        if (!foundPerson.isPresent()) {
            errors.rejectValue("name", "", "Человек с таким именем не существует");
        } else if (!foundPerson.get().getPassword().equals(person.getPassword())) {
            errors.rejectValue("password", "", "Пароль не совпадает");
        }
    }
}
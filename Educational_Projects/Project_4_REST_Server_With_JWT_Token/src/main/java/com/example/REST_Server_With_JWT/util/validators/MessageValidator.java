package com.example.REST_Server_With_JWT.util.validators;

import com.example.REST_Server_With_JWT.models.Message;
import com.example.REST_Server_With_JWT.models.Person;
import com.example.REST_Server_With_JWT.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

// данный класс предназначен для валидации сообщений, а именно поля name
@Component
public class MessageValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public MessageValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Message.class.equals(aClass);
    }

    // в данном методе валидируем поле name, если такого человека в БД не существует, выдаем ошибку
    @Override
    public void validate(Object o, Errors errors) {
        Message message = (Message) o;
        Optional<Person> foundPerson = personService.findByName(message.getName().getName());

        if (!foundPerson.isPresent()) {
            errors.rejectValue("name", "", "Человек с таким именем не существует");
        }
    }
}
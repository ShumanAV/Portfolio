package org.example.util;

import org.example.DAO.PersonDAO;
import org.example.Models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /*
    В этом методе даем понять Спрингу на объектах какого класса использовать данный валидатор, валидаторы нужно
    использовать на той сущности, для которой он предназначен, данный метод будет работать только если класс,
    который передается в данный метод будет равняться Person, этот метод как предохранитель правильного использования валидатора
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    /*
    В данном валидаторе проверяем на уникальность имя человека, в случае если человек с таким именем и отличающимся id
    уже есть в БД, записываем ошибку для данного поля
     */
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> personFromDB = personDAO.show(person.getName());
        if (personFromDB.isPresent() && person.getPersonId() != personFromDB.get().getPersonId()) {
            errors.rejectValue("name", "", "Человек с таким ФИО уже есть");
        }
    }
}

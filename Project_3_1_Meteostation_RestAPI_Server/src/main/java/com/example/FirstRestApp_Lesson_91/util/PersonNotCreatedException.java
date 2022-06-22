package com.example.FirstRestApp_Lesson_91.util;

// данный Exception о том, что в тех данных, которые прислал клиент, были ошибки и человек не был создан
public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String msg) {
        // будем принимать сообщение об ошибке и передавать его в супер класс RuntimeException
        super(msg);
    }
}

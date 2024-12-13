package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одной специализации доктора
public class SpecializationNotFoundException extends RuntimeException{
    public SpecializationNotFoundException(String msg) {
        super(msg);
    }
}

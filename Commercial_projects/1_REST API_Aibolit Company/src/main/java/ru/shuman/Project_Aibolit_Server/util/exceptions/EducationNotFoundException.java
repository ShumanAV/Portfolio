package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного образования родителя пациента
public class EducationNotFoundException extends RuntimeException{
    public EducationNotFoundException(String msg) {
        super(msg);
    }
}

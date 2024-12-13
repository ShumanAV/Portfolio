package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного типа занятости родителя пациента
public class TypeEmploymentNotFoundException extends RuntimeException{
    public TypeEmploymentNotFoundException(String msg) {
        super(msg);
    }
}

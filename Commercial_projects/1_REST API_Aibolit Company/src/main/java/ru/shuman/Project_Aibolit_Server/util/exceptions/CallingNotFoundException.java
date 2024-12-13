package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного вызова врача
public class CallingNotFoundException extends RuntimeException{
    public CallingNotFoundException(String msg) {
        super(msg);
    }
}

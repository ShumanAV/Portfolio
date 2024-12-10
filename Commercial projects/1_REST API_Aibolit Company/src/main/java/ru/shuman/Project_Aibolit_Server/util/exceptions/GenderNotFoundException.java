package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного гендера
public class GenderNotFoundException extends RuntimeException{
    public GenderNotFoundException(String msg) {
        super(msg);
    }
}

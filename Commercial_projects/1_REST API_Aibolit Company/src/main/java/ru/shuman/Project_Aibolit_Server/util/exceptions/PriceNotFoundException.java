package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного прайса
public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException(String msg) {
        super(msg);
    }
}

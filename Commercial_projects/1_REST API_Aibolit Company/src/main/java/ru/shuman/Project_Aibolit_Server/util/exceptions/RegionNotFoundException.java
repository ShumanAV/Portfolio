package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного региона
public class RegionNotFoundException extends RuntimeException{
    public RegionNotFoundException(String msg) {
        super(msg);
    }
}

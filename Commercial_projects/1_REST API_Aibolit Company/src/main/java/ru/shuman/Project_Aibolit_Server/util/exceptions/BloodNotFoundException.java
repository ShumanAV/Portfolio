package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одной группы крови
public class BloodNotFoundException extends RuntimeException{
    public BloodNotFoundException(String msg) {
        super(msg);
    }
}

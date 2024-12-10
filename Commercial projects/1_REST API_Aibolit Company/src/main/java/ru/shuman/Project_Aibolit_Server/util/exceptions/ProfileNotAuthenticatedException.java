package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе аутентификации
public class ProfileNotAuthenticatedException extends RuntimeException{
    public ProfileNotAuthenticatedException(String msg) {
        super(msg);
    }
}

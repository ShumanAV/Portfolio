package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException(String msg) {
        super(msg);
    }
}

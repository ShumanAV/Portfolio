package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String msg) {
        super(msg);
    }
}

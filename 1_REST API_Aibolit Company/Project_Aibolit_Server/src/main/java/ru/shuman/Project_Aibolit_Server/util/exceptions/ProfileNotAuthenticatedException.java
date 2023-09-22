package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class ProfileNotAuthenticatedException extends RuntimeException{
    public ProfileNotAuthenticatedException(String msg) {
        super(msg);
    }
}

package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class ProfileOrUserNotCreatedOrUpdatedException extends RuntimeException{
    public ProfileOrUserNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

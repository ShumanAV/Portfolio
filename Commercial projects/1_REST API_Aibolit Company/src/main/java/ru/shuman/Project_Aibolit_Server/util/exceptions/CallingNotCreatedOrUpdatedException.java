package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class CallingNotCreatedOrUpdatedException extends RuntimeException{
    public CallingNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

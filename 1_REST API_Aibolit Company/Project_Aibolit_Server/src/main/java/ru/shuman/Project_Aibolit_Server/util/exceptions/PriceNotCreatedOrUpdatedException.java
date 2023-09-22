package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class PriceNotCreatedOrUpdatedException extends RuntimeException{
    public PriceNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

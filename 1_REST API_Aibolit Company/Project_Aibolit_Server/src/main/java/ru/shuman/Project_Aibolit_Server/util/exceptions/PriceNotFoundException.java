package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException(String msg) {
        super(msg);
    }
}

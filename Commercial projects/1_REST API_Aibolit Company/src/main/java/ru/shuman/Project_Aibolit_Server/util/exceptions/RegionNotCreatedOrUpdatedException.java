package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class RegionNotCreatedOrUpdatedException extends RuntimeException{
    public RegionNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

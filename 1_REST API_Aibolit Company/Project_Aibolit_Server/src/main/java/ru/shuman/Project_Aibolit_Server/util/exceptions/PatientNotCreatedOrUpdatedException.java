package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class PatientNotCreatedOrUpdatedException extends RuntimeException{
    public PatientNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String msg) {
        super(msg);
    }
}

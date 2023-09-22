package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class PatientNotFound extends RuntimeException{
    public PatientNotFound(String msg) {
        super(msg);
    }
}

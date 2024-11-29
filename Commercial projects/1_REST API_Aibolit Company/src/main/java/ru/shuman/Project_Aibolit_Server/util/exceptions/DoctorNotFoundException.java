package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String msg) {
        super(msg);
    }
}

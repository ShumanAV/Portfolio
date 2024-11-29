package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class UserOrDoctorNotCreatedOrUpdatedException extends RuntimeException{
    public UserOrDoctorNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

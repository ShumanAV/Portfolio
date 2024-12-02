package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class ProfileOrDoctorNotCreatedOrUpdatedException extends RuntimeException{
    public ProfileOrDoctorNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

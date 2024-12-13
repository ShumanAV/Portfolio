package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта доктора или профиля
public class ProfileOrDoctorNotCreatedOrUpdatedException extends RuntimeException{
    public ProfileOrDoctorNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта пациента
public class PatientNotCreatedOrUpdatedException extends RuntimeException{
    public PatientNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

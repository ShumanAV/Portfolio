package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного пациента
public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String msg) {
        super(msg);
    }
}

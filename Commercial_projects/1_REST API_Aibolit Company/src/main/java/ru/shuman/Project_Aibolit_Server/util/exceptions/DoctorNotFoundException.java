package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного доктора
public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String msg) {
        super(msg);
    }
}

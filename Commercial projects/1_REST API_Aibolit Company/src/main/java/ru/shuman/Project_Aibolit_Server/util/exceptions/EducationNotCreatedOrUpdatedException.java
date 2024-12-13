package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта образования родителя пациента
public class EducationNotCreatedOrUpdatedException extends RuntimeException{
    public EducationNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта специализации доктора
public class SpecializationNotCreatedOrUpdatedException extends RuntimeException{
    public SpecializationNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

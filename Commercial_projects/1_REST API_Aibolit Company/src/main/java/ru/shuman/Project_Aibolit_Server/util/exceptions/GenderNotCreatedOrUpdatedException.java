package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта гендера
public class GenderNotCreatedOrUpdatedException extends RuntimeException{
    public GenderNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

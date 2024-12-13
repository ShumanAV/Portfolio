package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта вызова врача
public class CallingNotCreatedOrUpdatedException extends RuntimeException{
    public CallingNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

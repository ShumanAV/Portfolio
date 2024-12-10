package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта региона
public class RegionNotCreatedOrUpdatedException extends RuntimeException{
    public RegionNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

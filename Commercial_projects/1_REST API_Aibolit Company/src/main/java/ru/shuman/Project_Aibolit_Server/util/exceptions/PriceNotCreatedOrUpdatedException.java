package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта прайса
public class PriceNotCreatedOrUpdatedException extends RuntimeException{
    public PriceNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта группы крови
public class BloodNotCreatedOrUpdatedException extends RuntimeException{
    public BloodNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

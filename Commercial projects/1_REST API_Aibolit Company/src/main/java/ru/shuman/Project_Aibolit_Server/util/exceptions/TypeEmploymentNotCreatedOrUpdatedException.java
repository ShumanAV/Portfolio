package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта типа занятости родителя пациента
public class TypeEmploymentNotCreatedOrUpdatedException extends RuntimeException{
    public TypeEmploymentNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

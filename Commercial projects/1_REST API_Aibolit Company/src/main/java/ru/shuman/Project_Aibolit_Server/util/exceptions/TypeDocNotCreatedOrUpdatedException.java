package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта типа документа удостоверяющего личность
public class TypeDocNotCreatedOrUpdatedException extends RuntimeException{
    public TypeDocNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

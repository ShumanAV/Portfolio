package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного типа документа удостоверяющего личность
public class TypeDocNotFoundException extends RuntimeException{
    public TypeDocNotFoundException(String msg) {
        super(msg);
    }
}

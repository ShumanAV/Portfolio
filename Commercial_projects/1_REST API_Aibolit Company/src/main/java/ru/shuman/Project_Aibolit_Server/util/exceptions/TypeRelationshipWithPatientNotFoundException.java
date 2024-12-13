package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного типа отношений родителя с пациентом
public class TypeRelationshipWithPatientNotFoundException extends RuntimeException{
    public TypeRelationshipWithPatientNotFoundException(String msg) {
        super(msg);
    }
}

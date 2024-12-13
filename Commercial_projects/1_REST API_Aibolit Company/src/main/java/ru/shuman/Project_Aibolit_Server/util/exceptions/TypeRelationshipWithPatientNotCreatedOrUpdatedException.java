package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта типа отношений родителя с пациентом
public class TypeRelationshipWithPatientNotCreatedOrUpdatedException extends RuntimeException{
    public TypeRelationshipWithPatientNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного типа договора
public class TypeContractNotFoundException extends RuntimeException{
    public TypeContractNotFoundException(String msg) {
        super(msg);
    }
}

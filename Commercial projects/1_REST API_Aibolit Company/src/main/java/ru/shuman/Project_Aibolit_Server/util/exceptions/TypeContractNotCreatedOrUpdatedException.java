package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта типа договора
public class TypeContractNotCreatedOrUpdatedException extends RuntimeException{
    public TypeContractNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

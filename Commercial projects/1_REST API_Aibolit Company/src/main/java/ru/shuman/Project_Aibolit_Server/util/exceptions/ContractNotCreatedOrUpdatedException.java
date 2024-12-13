package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе создания или апдейта договора на медицинское обслуживание
public class ContractNotCreatedOrUpdatedException extends RuntimeException{
    public ContractNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

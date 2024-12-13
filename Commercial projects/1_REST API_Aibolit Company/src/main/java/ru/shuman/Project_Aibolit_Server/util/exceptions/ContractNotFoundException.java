package ru.shuman.Project_Aibolit_Server.util.exceptions;

//Класс ошибка возникающая в процессе запроса по id одного договора на медицинское обслуживание
public class ContractNotFoundException extends RuntimeException{
    public ContractNotFoundException(String msg) {
        super(msg);
    }
}

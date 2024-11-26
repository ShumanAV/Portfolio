package ru.shuman.Project_Aibolit_Server.util.exceptions;

public class ContractNotCreatedOrUpdatedException extends RuntimeException{
    public ContractNotCreatedOrUpdatedException(String msg) {
        super(msg);
    }
}

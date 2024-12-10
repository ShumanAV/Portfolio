package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeContract;
import ru.shuman.Project_Aibolit_Server.services.TypeContractService;

@Component
public class TypeContractValidator implements Validator {

    private final TypeContractService typeContractService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeContractValidator(TypeContractService typeContractService) {
        this.typeContractService = typeContractService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TypeContract.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //валидации пока нет
    }
}

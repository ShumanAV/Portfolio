package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeContract;
import ru.shuman.Project_Aibolit_Server.services.TypeContractService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

@Component
public class TypeContractValidator implements Validator {

    private final TypeContractService typeContractService;

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
        TypeContract typeContract = (TypeContract) o;

        String field = StandardMethods.searchNameFieldInTargetClass(errors, typeContract.getClass());
    }
}

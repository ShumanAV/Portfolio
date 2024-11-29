package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeContract;
import ru.shuman.Project_Aibolit_Server.services.TypeContractService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class TypeContractIdValidator implements Validator {

    private final TypeContractService typeContractService;

    @Autowired
    public TypeContractIdValidator(TypeContractService typeContractService) {
        this.typeContractService = typeContractService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TypeContract.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TypeContract typeContract = (TypeContract) o;

        String field = searchNameFieldInParentEntity(errors, typeContract.getClass());

        if (typeContract.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У типа договора отсутствует id!");

        } else if (typeContractService.findById(typeContract.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Тип договора с таким id не существует!");
        }
    }
}

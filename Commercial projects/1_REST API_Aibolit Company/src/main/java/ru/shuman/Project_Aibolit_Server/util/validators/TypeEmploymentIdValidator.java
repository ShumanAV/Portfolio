package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeEmployment;
import ru.shuman.Project_Aibolit_Server.services.TypeEmploymentService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class TypeEmploymentIdValidator implements Validator {

    private final TypeEmploymentService typeEmploymentService;

    @Autowired
    public TypeEmploymentIdValidator(TypeEmploymentService typeEmploymentService) {
        this.typeEmploymentService = typeEmploymentService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TypeEmployment.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TypeEmployment typeEmployment = (TypeEmployment) o;

        String field = searchNameFieldInParentEntity(errors, typeEmployment.getClass());

        if (typeEmployment.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У типа занятости родителя отсутствует id!");

        } else if (typeEmploymentService.findById(typeEmployment.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Тип занятости у родителя с таким id не существует!");
        }
    }
}

package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;
import ru.shuman.Project_Aibolit_Server.models.TypeEmployment;
import ru.shuman.Project_Aibolit_Server.services.TypeEmploymentService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class TypeEmploymentValidator implements Validator {

    private final TypeEmploymentService typeEmploymentService;

    @Autowired
    public TypeEmploymentValidator(TypeEmploymentService typeEmploymentService) {
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

        Optional<TypeEmployment> existingTypeEmployment = typeEmploymentService.findByName(typeEmployment.getName());
        if (existingTypeEmployment.isPresent() && typeEmployment.getId() != existingTypeEmployment.get().getId()) {
            errors.rejectValue(field == null ? "name" : field, "", "Тип занятости родителя с таким названием уже существует!");
        }
    }
}

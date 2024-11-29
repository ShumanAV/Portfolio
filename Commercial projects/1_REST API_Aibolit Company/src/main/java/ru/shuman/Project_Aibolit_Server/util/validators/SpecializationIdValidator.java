package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Specialization;
import ru.shuman.Project_Aibolit_Server.services.SpecializationService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class SpecializationIdValidator implements Validator {

    private final SpecializationService specializationService;

    @Autowired
    public SpecializationIdValidator(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Specialization.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Specialization specialization = (Specialization) o;

        String field = searchNameFieldInParentEntity(errors, specialization.getClass());

        if (specialization.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У специализации отсутствует id!");

        } else if (specializationService.findById(specialization.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Специализации с таким id не существует!");
        }
    }
}

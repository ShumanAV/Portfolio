package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Education;
import ru.shuman.Project_Aibolit_Server.services.EducationService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class EducationIdValidator implements Validator {

    private final EducationService educationService;

    @Autowired
    public EducationIdValidator(EducationService educationService) {
        this.educationService = educationService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Education.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Education education = (Education) o;

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, education.getClass());

        if (education.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У образования родителя отсутствует id!");

        } else if (educationService.findById(education.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Образование у родителя с таким id не существует!");
        }
    }
}

package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Gender;
import ru.shuman.Project_Aibolit_Server.services.GenderService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class GenderIdValidator implements Validator {

    private final GenderService genderService;

    @Autowired
    public GenderIdValidator(GenderService genderService) {
        this.genderService = genderService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Gender.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Gender gender = (Gender) o;

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, gender.getClass());

        if (gender.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У гендера отсутствует id!");

        } else if (genderService.findById(gender.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Гендер с таким id не существует!");
        }
    }
}

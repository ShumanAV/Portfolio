package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.models.Gender;
import ru.shuman.Project_Aibolit_Server.services.GenderService;

import java.util.Optional;

@Component
public class GenderValidator implements Validator {

    private final GenderService genderService;

    @Autowired
    public GenderValidator(GenderService genderService) {
        this.genderService = genderService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Gender.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Gender gender = (Gender) o;

        //Проверяем есть ли уже гендер с таким названием
        Optional<Gender> existingGender = genderService.findByName(gender.getName());
        if (existingGender.isPresent() && gender.getId() != existingGender.get().getId()) {
            errors.rejectValue("name", "", "Гендер с таким названием уже существует!");
        }
    }
}

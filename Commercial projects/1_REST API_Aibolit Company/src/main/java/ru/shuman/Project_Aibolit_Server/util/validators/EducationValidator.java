package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Education;
import ru.shuman.Project_Aibolit_Server.services.EducationService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class EducationValidator implements Validator {

    private final EducationService educationService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public EducationValidator(EducationService educationService) {
        this.educationService = educationService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Education.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Education education = (Education) o;

        //проверяем уникальность названия образования, есть ли уже в БД образование с таким названием и другим id
        Optional<Education> existingEducation = educationService.findByName(education.getName());
        if (existingEducation.isPresent() && education.getId() != existingEducation.get().getId()) {
            errors.rejectValue("name", "", "Образование с таким названием уже существует");
        }
    }
}

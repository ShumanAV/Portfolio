package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Education;
import ru.shuman.Project_Aibolit_Server.services.EducationService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class EducationIdValidator implements Validator {

    private final EducationService educationService;

    /*
    Внедрение зависимостей
     */
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

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, education.getClass());

        //проверяем есть ли id у образования
        if (education.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У образования родителя отсутствует id!");

            //проверяем есть ли в БД образование с таким id
        } else if (educationService.findById(education.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Образование у родителя с таким id не существует!");
        }
    }
}

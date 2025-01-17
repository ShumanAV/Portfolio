package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.services.BloodService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class BloodIdValidator implements Validator {

    private final BloodService bloodService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public BloodIdValidator(BloodService bloodService) {
        this.bloodService = bloodService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Blood.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Blood blood = (Blood) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, blood.getClass());

        //проверяем есть ли id у группы крови
        if (blood.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У группы крови отсутствует id!");

            //проверяем есть ли группа крови в БД с таким id
        } else if (bloodService.findById(blood.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id" : field, "", "Группы крови с таким id не существует!");
        }
    }
}

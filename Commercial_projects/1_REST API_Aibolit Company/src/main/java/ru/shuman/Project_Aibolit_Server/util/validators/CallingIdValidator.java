package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.services.CallingService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class CallingIdValidator implements Validator {

    private final CallingService callingService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public CallingIdValidator(CallingService callingService) {
        this.callingService = callingService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Calling.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Calling calling = (Calling) o;

        //проверяем есть ли id у вызова врача
        if (calling.getId() == null) {
            errors.rejectValue("id", "", "У вызова врача отсутствует id!");

            //проверяем есть ли вызов врача в БД с таким id
        } else if (callingService.findById(calling.getId()).isEmpty()) {
            errors.rejectValue("id", "", "Вызова врача с таким id не существует!");
        }

    }
}

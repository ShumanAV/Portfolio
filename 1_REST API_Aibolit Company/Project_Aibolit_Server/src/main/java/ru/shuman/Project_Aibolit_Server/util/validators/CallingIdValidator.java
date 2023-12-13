package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.services.CallingService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class CallingIdValidator implements Validator {

    private final CallingService callingService;

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

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, calling.getClass());

        if (calling.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У вызова отсутствует id!");

        } else if (callingService.findById(calling.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Вызова с таким id не существует!");
        }

    }
}

package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.services.ParentService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class ParentIdValidator implements Validator {

    private final ParentService parentService;

    @Autowired
    public ParentIdValidator(ParentService parentService) {
        this.parentService = parentService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Parent.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Parent parent = (Parent) o;

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, parent.getClass());

        if (parent.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У родителя отсутствует id!");

        } else if (parentService.findById(parent.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id" : field, "", "Родителя с таким id не существует!");
        }
    }
}

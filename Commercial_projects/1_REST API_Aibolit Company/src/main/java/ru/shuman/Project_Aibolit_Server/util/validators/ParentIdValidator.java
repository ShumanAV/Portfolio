package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.services.ParentService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class ParentIdValidator implements Validator {

    private final ParentService parentService;

    /*
    Внедрение зависимостей
     */
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

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, parent.getClass());

        //проверяем есть ли id у родителя
        if (parent.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У родителя отсутствует id!");

            //проверяем есть ли родитель в БД с таким id
        } else if (parentService.findById(parent.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id" : field, "", "Родителя с таким id не существует!");
        }
    }
}

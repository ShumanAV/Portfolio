package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.services.RoleService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class RoleValidator implements Validator {

    private final RoleService roleService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RoleValidator(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Role.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //т.к. роли создаем напрямую из бэка, валидации роли нет
    }
}

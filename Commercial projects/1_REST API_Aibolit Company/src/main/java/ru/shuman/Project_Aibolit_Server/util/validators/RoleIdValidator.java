package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.services.RoleService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class RoleIdValidator implements Validator {

    private final RoleService roleService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RoleIdValidator(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Role.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Role role = (Role) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, role.getClass());

        //проверяем есть ли id у роли
        if (role.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У роли отсутствует id!");

            //проверяем есть ли роль в БД с таким id
        } else if (roleService.findById(role.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Роли с таким id не существует!");
        }
    }
}

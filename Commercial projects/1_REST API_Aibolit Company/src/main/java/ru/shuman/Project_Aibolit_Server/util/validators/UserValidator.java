package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.services.UserService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class UserValidator implements Validator {

    private final UserService userService;
    private final RoleIdValidator roleIdValidator;
    private final RoleValidator roleValidator;

    @Autowired
    public UserValidator(UserService userService, RoleIdValidator roleIdValidator, RoleValidator roleValidator) {
        this.userService = userService;
        this.roleIdValidator = roleIdValidator;
        this.roleValidator = roleValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        String field = searchNameFieldInParentEntity(errors, user.getClass());

        // Блок проверки наличия имени пользователя у профайла
        if (user.getUsername() == null || user.getUsername().equals("")) {
            errors.rejectValue(field == null ? "username": field, "", "Имя пользователя не заполнено!");

        } else {

            // Блок проверки отсутствия пользователя с таким же именем пользователя в профайле
            Optional<User> existingUser = userService.findByUsername(user.getUsername());
            if (existingUser.isPresent() &&
                    user.getId() != existingUser.get().getId()) {
                errors.rejectValue(field == null ? "username": field, "", "Пользователь с таким именем пользователя уже существует!");
            }
        }

        // Блок проверки наличия пароля
        if (user.getPassword() == null || user.getPassword().equals("")) {
            errors.rejectValue(field == null ? "password": field, "", "Пароль пользователя не заполнен!");
        }

        // Блок проверки наличия роли у профайла
        if (user.getRole() == null) {
            errors.rejectValue(field == null ? "role": field, "", "У пользователя не выбрана роль!");

        } else {
            // Блок проверки наличия Id у роли
            roleIdValidator.validate(user.getRole(), errors);
            roleValidator.validate(user.getRole(), errors);
        }

    }
}

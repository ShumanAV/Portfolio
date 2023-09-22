package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.services.UserService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

@Component
public class UserIdValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserIdValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        String field = StandardMethods.searchNameFieldInTargetClass(errors, user.getClass());

        // Блок проверки наличия и валидности id у пользователя при его наличии
        if (user.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У пользователя отсутствует id!");

        } else if (userService.findById(user.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id" : field, "", "Пользователя с таким id не существует!");
        }
    }
}

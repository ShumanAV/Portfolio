package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.services.UserService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserService userService;
    private final SpecializationValidator specializationValidator;
    private final SpecializationIdValidator specializationIdValidator;
    private final ProfileValidator profileValidator;
    private final ProfileIdValidator profileIdValidator;

    @Autowired
    public UserValidator(UserService userService, SpecializationValidator specializationValidator,
                         SpecializationIdValidator specializationIdValidator, ProfileValidator profileValidator, ProfileIdValidator profileIdValidator) {
        this.userService = userService;
        this.specializationValidator = specializationValidator;
        this.specializationIdValidator = specializationIdValidator;
        this.profileValidator = profileValidator;
        this.profileIdValidator = profileIdValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    // Использование PersonDetailsService в валидаторе не очень хорошая практика,
    // нужно сделать отдельный PeopleService
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        String field = StandardMethods.searchNameFieldInTargetClass(errors, user.getClass());

        // Блок проверки отсутствия пользователя с таким телефоном
        Optional<User> existingUser = userService.findByPhone(user.getPhone());
        if (existingUser.isPresent() && user.getId() != existingUser.get().getId()) {
            errors.rejectValue(field == null ? "phone": field, "", "Пользователь с таким номером телефона уже существует!");
        }

        // Блок проверки отсутствия пользователя с таким СНИЛС
        existingUser = userService.findBySnils(user.getSnils());
        if (existingUser.isPresent() && user.getId() != existingUser.get().getId()) {
            errors.rejectValue(field == null ? "snils": field, "", "Пользователь с таким номером СНИЛС уже существует!");
        }

        // Блок проверки отсутствия пользователя с таким ИНН
        existingUser = userService.findByInn(user.getInn());
        if (existingUser.isPresent() && user.getId() != existingUser.get().getId()) {
            errors.rejectValue(field == null ? "inn": field, "", "Пользователь с таким номером ИНН уже существует!");
        }

        // Блог проверки наличия специализации у пользователя
        if (user.getSpecialization() == null) {
            errors.rejectValue(field == null ? "specialization": field, "", "Поле специализация не заполнено!");

        } else {
            // Блог проверки наличия Id специализации у пользователя
            specializationIdValidator.validate(user.getSpecialization(), errors);
            specializationValidator.validate(user.getSpecialization(), errors);
        }

        // Блок проверки профиля пользователя
        if (user.isAccessToSystem()) {

            // Блок проверки наличия профайла у пользователя
            if (user.getProfile() == null) {
                errors.rejectValue(field == null ? "profile": field, "", "У пользователя есть доступ к системе, но отсутствует профиль!");
            } else {
                if (user.getProfile().getId() != null) {
                    profileIdValidator.validate(user.getProfile(), errors);
                }
                profileValidator.validate(user.getProfile(), errors);
            }

        } else {

            // Блок проверки отсутствия профайла у пользователя в случае отсутствия доступа к системе
            if (user.getProfile() != null) {
                errors.rejectValue(field == null ? "profile": field, "", "У данного пользователя нет доступа к системе, но при этом есть профайл, его не должно быть!");
            }
        }
    }
}

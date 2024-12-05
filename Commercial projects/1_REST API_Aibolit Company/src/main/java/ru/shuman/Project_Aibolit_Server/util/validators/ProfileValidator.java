package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.services.ProfileService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class ProfileValidator implements Validator {

    private final ProfileService profileService;
    private final RoleIdValidator roleIdValidator;
    private final RoleValidator roleValidator;

    @Autowired
    public ProfileValidator(ProfileService profileService, RoleIdValidator roleIdValidator, RoleValidator roleValidator) {
        this.profileService = profileService;
        this.roleIdValidator = roleIdValidator;
        this.roleValidator = roleValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Profile.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Profile profile = (Profile) o;

        String field = searchNameFieldInParentEntity(errors, profile.getClass());

        // Блок проверки наличия имени пользователя у профиля
        if (profile.getUsername() == null || profile.getUsername().equals("")) {
            errors.rejectValue(field == null ? "username": field, "", "Имя пользователя не заполнено!");

        } else {

            // Блок проверки отсутствия пользователя с таким же именем пользователя в профайле
            Optional<Profile> existingProfile = profileService.findByUsername(profile.getUsername());
            if (existingProfile.isPresent() &&
                    profile.getId() != existingProfile.get().getId()) {
                errors.rejectValue(field == null ? "username": field, "", "Профиль с таким именем пользователя уже существует!");
            }
        }

        // Блок проверки наличия пароля
        if (profile.getPassword() == null || profile.getPassword().equals("")) {
            errors.rejectValue(field == null ? "password": field, "", "Пароль пользователя не заполнен!");
        }

        // Блок проверки наличия роли у профиля
        if (profile.getRole() == null) {
            errors.rejectValue(field == null ? "role": field, "", "У профиля не выбрана роль!");

        } else {
            // Блок валидации роли
            roleIdValidator.validate(profile.getRole(), errors);
            roleValidator.validate(profile.getRole(), errors);
        }

    }
}

package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.services.ProfileService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class ProfileIdValidator implements Validator {

    private final ProfileService profileService;

    @Autowired
    public ProfileIdValidator(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Profile.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Profile profile = (Profile) o;

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, profile.getClass());

        if (profile.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У профиля отсутствует id!");

        } else if (profileService.findById(profile.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Профиля с таким id не существует!");
        }
    }
}

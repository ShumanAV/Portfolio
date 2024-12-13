package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Profile;
import ru.shuman.Project_Aibolit_Server.services.ProfileService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class ProfileIdValidator implements Validator {

    private final ProfileService profileService;

    /*
    Внедрение зависимостей
     */
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

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, profile.getClass());

        //проверяем есть ли id у группы крови
        if (profile.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У профиля отсутствует id!");

            //проверяем есть ли профиль в БД с таким id
        } else if (profileService.findById(profile.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Профиля с таким id не существует!");
        }
    }
}

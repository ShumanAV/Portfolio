package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.PlaceStudy;
import ru.shuman.Project_Aibolit_Server.services.PlaceStudyService;

@Component
public class PlaceStudyValidator implements Validator {

    private final PlaceStudyService placeStudyService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PlaceStudyValidator(PlaceStudyService placeStudyService) {
        this.placeStudyService = placeStudyService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PlaceStudy.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}

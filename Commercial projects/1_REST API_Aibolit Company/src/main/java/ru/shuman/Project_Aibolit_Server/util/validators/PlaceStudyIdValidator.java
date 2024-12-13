package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.PlaceStudy;
import ru.shuman.Project_Aibolit_Server.services.PlaceStudyService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class PlaceStudyIdValidator implements Validator {

    private final PlaceStudyService placeStudyService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PlaceStudyIdValidator(PlaceStudyService placeStudyService) {
        this.placeStudyService = placeStudyService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PlaceStudy.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PlaceStudy placeStudy = (PlaceStudy) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, placeStudy.getClass());

        //проверяем есть ли id у места учебы
        if (placeStudy.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У места учебы пациента отсутствует id!");

            //проверяем есть ли место учебы в БД с таким id
        } else if (placeStudyService.findById(placeStudy.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Места учебы пациента с таким id не существует!");
        }
    }
}

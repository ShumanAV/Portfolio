package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.services.RegionService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class RegionIdValidator implements Validator {

    private final RegionService regionService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RegionIdValidator(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Region.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Region region = (Region) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, region.getClass());

        //проверяем есть ли id у региона
        if (region.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У региона отсутствует id!");

            //проверяем есть ли регион в БД с таким id
        } else if (regionService.findById(region.getId()).isEmpty()) {
                errors.rejectValue(field == null ? "id": field, "", "Региона с таким id не существует!");
        }
    }
}

package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.services.RegionService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class RegionValidator implements Validator {

    private final RegionService regionService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RegionValidator(RegionService regionService) {
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

        //проверяем есть ли код региона
        if (region.getCode() == null || region.getCode().equals("")) {
            errors.rejectValue(field == null ? "code" : field, "", "У региона отсутствует код!");
        } else {
            //проверяем уникальность кода региона, есть ли уже регион с таким кодом региона и другим id
            Optional<Region> existingRegion = regionService.findByCode(region.getCode());
            if (existingRegion.isPresent() && region.getId() != existingRegion.get().getId()) {
                errors.rejectValue(field == null ? "code" : field, "", "Регион с таким кодом уже существует!");
            }
        }

        //проверяем есть ли имя региона
        if (region.getName() != null) {
            //проверяем уникальность названия региона, есть ли уже регион с таким названием региона и другим id
            Optional<Region> existingRegion = regionService.findByName(region.getName());
            if (existingRegion.isPresent() && region.getId() != existingRegion.get().getId()) {
                errors.rejectValue(field == null ? "name" : field, "", "Регион с таким названием уже существует!");
            }
        }

    }
}

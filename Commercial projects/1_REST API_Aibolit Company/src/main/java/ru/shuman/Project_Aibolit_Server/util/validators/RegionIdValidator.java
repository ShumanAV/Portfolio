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

        String field = searchNameFieldInParentEntity(errors, region.getClass());

        if (region.getName() == null || region.getName().equals("")) {
            errors.rejectValue(field == null ? "name": field, "", "У региона отсутствует наименование!");

        } else if (regionService.findByName(region.getName()).isEmpty()) {
                errors.rejectValue(field == null ? "name": field, "", "Региона с таким наименованием не существует!");
        }
    }
}

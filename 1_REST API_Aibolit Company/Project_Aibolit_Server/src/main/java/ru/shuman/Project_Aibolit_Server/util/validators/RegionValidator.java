package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.services.RegionService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

@Component
public class RegionValidator implements Validator {

    private final RegionService regionService;

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

        String field = StandardMethods.searchNameFieldInTargetClass(errors, region.getClass());

        if (region.getCode() == null || region.getCode().equals("")) {
            errors.rejectValue(field == null ? "code": field, "", "У региона отсутствует код!");
        }
    }
}

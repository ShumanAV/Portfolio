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

        String field = searchNameFieldInParentEntity(errors, region.getClass());

        if (region.getCode() == null || region.getCode().equals("")) {
            errors.rejectValue(field == null ? "code" : field, "", "У региона отсутствует код!");
        } else {
            Optional<Region> existingRegion = regionService.findByCode(region.getCode());
            if (existingRegion.isPresent() && region.getId() != existingRegion.get().getId()) {
                errors.rejectValue(field == null ? "code" : field, "", "Регион с таким кодом уже существует!");
            }
        }

        Optional<Region> existingRegion = regionService.findByName(region.getName());
        if (existingRegion.isPresent() && region.getId() != existingRegion.get().getId()) {
            errors.rejectValue(field == null ? "name" : field, "", "Регион с таким названием уже существует!");
        }

    }
}

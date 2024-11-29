package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Specialization;
import ru.shuman.Project_Aibolit_Server.services.SpecializationService;

@Component
public class SpecializationValidator implements Validator {

    private final SpecializationService specializationService;

    @Autowired
    public SpecializationValidator(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Specialization.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}

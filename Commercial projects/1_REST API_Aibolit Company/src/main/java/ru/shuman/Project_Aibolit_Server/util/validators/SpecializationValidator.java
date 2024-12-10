package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Specialization;
import ru.shuman.Project_Aibolit_Server.services.SpecializationService;

import java.util.Optional;

@Component
public class SpecializationValidator implements Validator {

    private final SpecializationService specializationService;

    /*
    Внедрение зависимостей
     */
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
        Specialization specialization = (Specialization) o;

        //проверяем есть ли название специализации
        if (specialization.getName() != null) {
            //проверяем уникальность названия специализации, есть ли уже специализация с таким названием
            Optional<Specialization> existingSpecialization = specializationService.findByName(specialization.getName());
            if (existingSpecialization.isPresent() && specialization.getId() != existingSpecialization.get().getId()) {
                errors.rejectValue("name", "", "Специализация с таким названием уже существует!");
            }
        }
    }
}

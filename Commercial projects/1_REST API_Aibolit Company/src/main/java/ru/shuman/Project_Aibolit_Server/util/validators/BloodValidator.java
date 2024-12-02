package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.models.Specialization;
import ru.shuman.Project_Aibolit_Server.services.BloodService;

import java.util.Optional;

@Component
public class BloodValidator implements Validator {

    private final BloodService bloodService;

    @Autowired
    public BloodValidator(BloodService bloodService) {
        this.bloodService = bloodService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Blood.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Blood blood = (Blood) o;

        Optional<Blood> existingBlood = bloodService.findByName(blood.getName());
        if (existingBlood.isPresent() && blood.getId() != existingBlood.get().getId()) {
            errors.rejectValue("name", "", "Группа крови с таким названием уже существует!");
        }
    }
}

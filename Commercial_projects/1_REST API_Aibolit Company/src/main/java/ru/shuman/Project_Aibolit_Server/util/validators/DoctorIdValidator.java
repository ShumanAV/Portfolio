package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.services.DoctorService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class DoctorIdValidator implements Validator {

    private final DoctorService doctorService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public DoctorIdValidator(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Doctor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Doctor doctor = (Doctor) o;

        //проверяем есть ли id у доктора
        if (doctor.getId() == null) {
            errors.rejectValue("id", "", "У пользователя отсутствует id!");

            //проверяем есть ли доктор в БД с таким id
        } else if (doctorService.findById(doctor.getId()).isEmpty()) {
            errors.rejectValue("id", "", "Пользователя с таким id не существует!");
        }
    }
}

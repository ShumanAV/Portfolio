package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.services.PatientService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class PatientIdValidator implements Validator {

    private final PatientService patientService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PatientIdValidator(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Patient.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Patient patient = (Patient) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, patient.getClass());

        //проверяем есть ли id у пациента
        if (patient.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У пациента отсутствует id!");

            //проверяем есть ли пациент в БД с таким id
        } else if (patientService.findById(patient.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id" : field, "", "Пациента с таким id не существует!");
        }
    }
}

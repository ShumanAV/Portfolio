package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.services.SensorsService;

/*
Класс для валидации сущности измерения
 */

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    /*
    Метод осуществляет проверку при создании нового измерения на наличие сенсора в БД, который указан в измерении,
    в случае отсутствия формирует ошибку для данного поля
     */
    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() == null) {
            return;
        }

        if (sensorsService.findByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "There is no sensor with that name");
        }
    }
}

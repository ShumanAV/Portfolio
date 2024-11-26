package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.services.SensorsService;

/*
Класс для валидации сущности сенсора
 */

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }


    /*
    Метод осуществляет проверку при создании нового сенсора на наличие сенсора с таким названием в БД,
    в случае наличия формирует ошибку для данного поля
    */
    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorsService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Sensor with that name has already been registered");
        }
    }
}

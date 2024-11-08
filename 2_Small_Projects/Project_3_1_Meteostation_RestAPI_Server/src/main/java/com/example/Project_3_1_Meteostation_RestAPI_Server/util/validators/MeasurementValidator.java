package com.example.Project_3_1_Meteostation_RestAPI_Server.util.validators;

import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import com.example.Project_3_1_Meteostation_RestAPI_Server.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;

        if (measurement.getSensor() == null) {
            return;
        }

        if (!sensorService.findByName(measurement.getSensor().getName()).isPresent()) {
            errors.rejectValue("sensor", "", "Sensor with that name does not exist");
        }
    }
}

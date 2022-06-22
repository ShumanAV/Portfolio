package com.example.Project_3_1_Meteostation_RestAPI_Server.util;

import com.example.Project_3_1_Meteostation_RestAPI_Server.dto.MeasurementDTO;
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
        return MeasurementDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) o;

        if (!sensorService.findByName(measurementDTO.getSensor()).isPresent()) {
            errors.rejectValue("name", "", "Sensor with that name does not exist");
        }
    }
}

package com.example.Project_3_1_Meteostation_RestAPI_Server.Controllers;

import com.example.Project_3_1_Meteostation_RestAPI_Server.dto.SensorDTO;
import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;
import com.example.Project_3_1_Meteostation_RestAPI_Server.services.SensorService;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.responses.ErrorResponse;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.ConverterDTOModel;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.CreateMessageException;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.exceptions.SensorAndMeasurementException;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.validators.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class SensorController {

    private final ConverterDTOModel modelMapper;
    private final SensorValidator sensorValidator;
    private final SensorService sensorService;

    @Autowired
    public SensorController(ConverterDTOModel modelMapper,
                            SensorValidator sensorValidator,
                            SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
        this.sensorService = sensorService;
    }

    @PostMapping("/sensors/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        sensorValidator.validate(modelMapper.convert(sensorDTO, Sensor.class), bindingResult);

        if (bindingResult.hasErrors()) {
            CreateMessageException.returnErrorsToClient(bindingResult);
        }

        sensorService.save((Sensor) modelMapper.convert(sensorDTO, Sensor.class));

        // вернется клиенту, что все прошло ок, т.е. отправляем HTTP ответ с пустым телом и статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // в этом методе ловим выброшенный MeasurementException
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorAndMeasurementException e) {
        // создаем наш объект ErrorResponse с ошибкой
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

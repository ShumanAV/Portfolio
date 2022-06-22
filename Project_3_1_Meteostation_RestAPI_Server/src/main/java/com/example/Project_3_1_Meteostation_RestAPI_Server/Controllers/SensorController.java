package com.example.Project_3_1_Meteostation_RestAPI_Server.Controllers;

import com.example.Project_3_1_Meteostation_RestAPI_Server.dto.SensorDTO;
import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;
import com.example.Project_3_1_Meteostation_RestAPI_Server.services.SensorService;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.SensorErrorResponse;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.exception.SensorNotCreatedException;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SensorController {

    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;
    private final SensorService sensorService;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorValidator sensorValidator, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
        this.sensorService = sensorService;
    }

    @PostMapping("/sensors/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            // создадим лист ошибок
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                // преобразуем все ошибки сделанные клиентом в большую строку
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage()).append(";");
            }
            // выбросим нашу ошибку и передадим в нее текст ошибки валидации данных, нам остается ее поймать в другом методе ниже
            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));
        // вернется клиенту, что все прошло ок, т.е. отправляем HTTP ответ с пустым телом и статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        // создаем наш объект PersonErrorResponse с ошибкой
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // метод конвертации из DTO в модель
    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    // метод конвертации из модели в DTO
    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}

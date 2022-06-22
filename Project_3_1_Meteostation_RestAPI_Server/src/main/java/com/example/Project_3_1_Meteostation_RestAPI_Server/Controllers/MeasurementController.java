package com.example.Project_3_1_Meteostation_RestAPI_Server.Controllers;

import com.example.Project_3_1_Meteostation_RestAPI_Server.dto.MeasurementDTO;
import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import com.example.Project_3_1_Meteostation_RestAPI_Server.services.MeasurementService;
import com.example.Project_3_1_Meteostation_RestAPI_Server.services.SensorService;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.SensorErrorResponse;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.MeasurementValidator;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.exception.MeasurementNotCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MeasurementController {

    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(MeasurementValidator measurementValidator,
                                 ModelMapper modelMapper,
                                 MeasurementService measurementService,
                                 SensorService sensorService) {
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @GetMapping("/measurements")
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementService.findAllMeasurements()
                .stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("//measurements/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementService.returnRainyDaysCount();
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        measurementValidator.validate(measurementDTO, bindingResult);

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
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurement.setSensor(sensorService.findByName(measurementDTO.getSensor()).get());
        measurementService.save(measurement);
        // вернется клиенту, что все прошло ок, т.е. отправляем HTTP ответ с пустым телом и статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(MeasurementNotCreatedException e) {
        // создаем наш объект PersonErrorResponse с ошибкой
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

}
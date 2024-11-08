package com.example.Project_3_1_Meteostation_RestAPI_Server.Controllers;

import com.example.Project_3_1_Meteostation_RestAPI_Server.dto.MeasurementDTO;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.responses.MeasurementsResponse;
import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import com.example.Project_3_1_Meteostation_RestAPI_Server.services.MeasurementService;
import com.example.Project_3_1_Meteostation_RestAPI_Server.services.SensorService;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.responses.ErrorResponse;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.CreateMessageException;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.validators.MeasurementValidator;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.ConverterDTOModel;
import com.example.Project_3_1_Meteostation_RestAPI_Server.util.exceptions.SensorAndMeasurementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class MeasurementController {

    private final ConverterDTOModel modelMapper;
    private final MeasurementValidator measurementValidator;
    private final MeasurementService measurementService;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(ConverterDTOModel modelMapper,
                                 MeasurementValidator measurementValidator,
                                 MeasurementService measurementService,
                                 SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    // считается хорошим тоном отправлять список элементов в обертке - обычно оборачиваем во внешний объект, а не просто список
    @GetMapping("/measurements")
    public MeasurementsResponse getAllMeasurements() {
        return new MeasurementsResponse(measurementService.findAllMeasurements()
                .stream()
                .map(measurement -> (MeasurementDTO) modelMapper.convert(measurement, MeasurementDTO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/measurements/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementService.returnRainyDaysCount();
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        measurementValidator.validate(modelMapper.convert(measurementDTO, Measurement.class), bindingResult);

        if (bindingResult.hasErrors()) {
            CreateMessageException.returnErrorsToClient(bindingResult);
        }

        Measurement measurement = (Measurement) modelMapper.convert(measurementDTO, Measurement.class);
        measurementService.save(measurement);

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
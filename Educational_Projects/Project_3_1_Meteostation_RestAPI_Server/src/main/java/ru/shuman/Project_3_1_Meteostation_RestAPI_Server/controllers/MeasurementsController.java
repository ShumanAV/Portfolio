package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.dto.MeasurementDTO;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.dto.MeasurementResponseDTO;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.services.MeasurementsService;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.errors.MeasurementErrorResponse;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.exceptions.MeasurementNotCreatedException;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.validators.MeasurementValidator;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.ErrorsUtil;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator measurementValidator,
                                  ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод запрашивает и отправляет список всех измерений
     */
    @GetMapping()
    public ResponseEntity<MeasurementResponseDTO> getAllMeasurements() {
        return new ResponseEntity<>(new MeasurementResponseDTO(measurementsService.findAll().stream()
                .map(this::convertToMeasurementDTO).toList()), HttpStatus.OK);
    }

    /*
    Метод запрашивает и отправляет количество дождливых дней
     */
    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Long> getRainyDaysCount() {
        return new ResponseEntity<>(measurementsService.getRainyDays(), HttpStatus.OK);
    }

    /*
    Метод получает новое измерение, валидирует все поля и сохраняет,
    в случае отсутствия ошибок при валидации отправляет ответ 200 - ОК
     */
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        Measurement measurementToAdd = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            ErrorsUtil.returnErrorsToClient(bindingResult, MeasurementNotCreatedException.class);
        }

        measurementsService.register(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /*
    Метод отлавливает ошибку при создании нового измерения MeasurementNotCreatedException, которая возникает в случае валидации,
    возвращает текст ошибок и время возникновения ошибок в виде обертки MeasurementErrorResponse
     */
    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конвертирует из модели измерения в DTO при помощи класса ModelMapper
     */
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    /*
    Метод конвертирует из DTO в модель измерения при помощи класса ModelMapper
    */
    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}

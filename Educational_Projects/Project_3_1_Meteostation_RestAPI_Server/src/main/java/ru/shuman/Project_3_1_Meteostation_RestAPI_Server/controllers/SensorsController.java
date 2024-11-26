package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.dto.SensorDTO;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.services.SensorsService;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.errors.SensorErrorResponse;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.exceptions.SensorNotCreatedException;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.validators.SensorValidator;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.ErrorsUtil;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;
    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод запрашивает и отправляет все сенсоры
     */
    @GetMapping()
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        return new ResponseEntity<>(sensorsService.findAll().stream().map(this::convertToSensorDTO).toList(), HttpStatus.OK);
    }

    /*
    Метод получает новый сенсор, валидирует все поля, плюс валидирует в валидаторе на вопрос наличия такого сенсора в БД,
    в случае отсутствия ошибок при валидации сохраняет и отправляет ответ 200 - ОК
     */
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        Sensor sensorToAdd = convertToSensor(sensorDTO);

        sensorValidator.validate(sensorToAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            ErrorsUtil.returnErrorsToClient(bindingResult, SensorNotCreatedException.class);
        }

        sensorsService.register(sensorToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /*
    Метод отлавливает ошибку при создании нового сенсора SensorNotCreatedException, которая возникает в случае валидации,
    возвращает текст ошибок и время возникновения ошибок в виде обертки SensorErrorResponse
    */
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конвертирует из DTO в модель сенсора при помощи класса ModelMapper
    */
    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    /*
    Метод конвертирует из модели сенсора в DTO при помощи класса ModelMapper
    */
    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}

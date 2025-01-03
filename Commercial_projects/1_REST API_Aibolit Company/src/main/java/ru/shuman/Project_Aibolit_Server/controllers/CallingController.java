package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.CallingDTO;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.services.CallingService;
import ru.shuman.Project_Aibolit_Server.util.errors.CallingErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.CallingNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.CallingNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.CallingIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.CallingValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/callings")
public class CallingController {

    private final CallingService callingService;
    private final CallingValidator callingValidator;
    private final CallingIdValidator callingIdValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public CallingController(CallingService callingService, CallingValidator callingValidator, CallingIdValidator callingIdValidator, ModelMapper modelMapper) {
        this.callingService = callingService;
        this.callingValidator = callingValidator;
        this.callingIdValidator = callingIdValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список вызовов врачей в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<CallingDTO>> sendListCallings() {

        List<Calling> callings = callingService.findAll();

        List<CallingDTO> callingDTOList = callings.stream().map(this::convertToCallingDTO).collect(Collectors.toList());

        return new ResponseEntity<>(callingDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один вызов врача по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа Calling, устанавливаем в нем переданный id, далее валидируем id,
    находим вызов и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<CallingDTO> sendOneCalling(@PathVariable(value = "id") int callingId,
                                                     @ModelAttribute(value = "calling") Calling calling,
                                                     BindingResult bindingResult) {

        calling.setId(callingId);

        callingIdValidator.validate(calling, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, CallingNotFoundException.class);

        CallingDTO callingDTO = convertToCallingDTO(callingService.findById(callingId).get());

        return new ResponseEntity<>(callingDTO, HttpStatus.OK);
    }

    /*
    Метод создает новый вызов врача, на вход поступает объект CallingDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый вызов, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CallingDTO callingDTO,
                                             BindingResult bindingResult) {

        Calling calling = convertToCalling(callingDTO);

        callingValidator.validate(calling, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, CallingNotCreatedOrUpdatedException.class);

        callingService.create(calling);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий вызов врача, в URL передается id и в виде json объект CallingDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int callingId,
                                             @RequestBody @Valid CallingDTO callingDTO,
                                             BindingResult bindingResult) {
        callingDTO.setId(callingId);
        Calling calling = convertToCalling(callingDTO);

        callingIdValidator.validate(calling, bindingResult);
        callingValidator.validate(calling, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, CallingNotCreatedOrUpdatedException.class);

        callingService.update(calling);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения CallingNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<CallingErrorResponse> handleException(CallingNotFoundException e) {
        CallingErrorResponse response = new CallingErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения CallingNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<CallingErrorResponse> handleException(CallingNotCreatedOrUpdatedException e) {
        CallingErrorResponse response = new CallingErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private Calling convertToCalling(CallingDTO callingDTO) {
        return this.modelMapper.map(callingDTO, Calling.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private CallingDTO convertToCallingDTO(Calling calling) {
        return this.modelMapper.map(calling, CallingDTO.class);
    }
}

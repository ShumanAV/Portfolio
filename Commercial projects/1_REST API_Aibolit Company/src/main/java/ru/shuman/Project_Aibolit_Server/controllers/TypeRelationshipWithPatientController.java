package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.TypeRelationshipWithPatientDTO;
import ru.shuman.Project_Aibolit_Server.models.TypeRelationshipWithPatient;
import ru.shuman.Project_Aibolit_Server.services.TypeRelationshipWithPatientService;
import ru.shuman.Project_Aibolit_Server.util.errors.TypeRelationshipWithPatientErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeRelationshipWithPatientNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeRelationshipWithPatientNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeRelationshipWithPatientIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeRelationshipWithPatientValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/typesRelationships")
public class TypeRelationshipWithPatientController {

    private final TypeRelationshipWithPatientService typeRelationshipService;
    private final TypeRelationshipWithPatientIdValidator typeRelationshipIdValidator;
    private final TypeRelationshipWithPatientValidator typeRelationshipValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeRelationshipWithPatientController(TypeRelationshipWithPatientService typeRelationshipService,
                                                 TypeRelationshipWithPatientIdValidator typeRelationshipIdValidator,
                                                 TypeRelationshipWithPatientValidator typeRelationshipValidator, ModelMapper modelMapper) {
        this.typeRelationshipService = typeRelationshipService;
        this.typeRelationshipIdValidator = typeRelationshipIdValidator;
        this.typeRelationshipValidator = typeRelationshipValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список типов отношений родителей с пациентами в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<TypeRelationshipWithPatientDTO>> sendListTypesRelationshipWithPatient() {

        List<TypeRelationshipWithPatient> relationshipList = typeRelationshipService.findAll();

        List<TypeRelationshipWithPatientDTO> relationshipDTOList = relationshipList.stream().
                map(this::convertToTypeRelationshipDTO).collect(Collectors.toList());

        return new ResponseEntity<>(relationshipDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один тип отношения родителей с пациентом по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа TypeRelationshipWithPatient,
    устанавливаем в нем переданный id, далее валидируем id, находим тип отношений и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeRelationshipWithPatientDTO> sendOneTypeRelationshipWithPatient(
            @PathVariable(value = "id") int typeRelationshiptId,
            @ModelAttribute(value = "typeRelationshipWithPatient") TypeRelationshipWithPatient typeRelationshipWithPatient,
            BindingResult bindingResult) {

        typeRelationshipWithPatient.setId(typeRelationshiptId);

        typeRelationshipIdValidator.validate(typeRelationshipWithPatient, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeRelationshipWithPatientNotFoundException.class);

        TypeRelationshipWithPatientDTO typeRelationshipWithPatientDTO =
                convertToTypeRelationshipDTO(typeRelationshipService.findById(typeRelationshiptId).get());

        return new ResponseEntity<>(typeRelationshipWithPatientDTO, HttpStatus.OK);

    }

    /*
    Метод создает новый тип отношений родителя с пациентом, на вход поступает объект TypeRelationshipWithPatientDTO
    в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый тип отношений, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TypeRelationshipWithPatientDTO typeRelationshipWithPatientDTO,
                                             BindingResult bindingResult) {

        TypeRelationshipWithPatient typeRelationshipWithPatient = convertToTypeRelationship(typeRelationshipWithPatientDTO);

        typeRelationshipValidator.validate(typeRelationshipWithPatient, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeRelationshipWithPatientNotCreatedOrUpdatedException.class);

        typeRelationshipService.create(typeRelationshipWithPatient);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий тип отношений родителя с пациентом, в URL передается id и в виде json объект
    TypeRelationshipWithPatientDTO с новыми данными для изменения, валидируем его, при отсутствии ошибок
    сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int typeRelationshipWithPatientId,
                                             @RequestBody @Valid TypeRelationshipWithPatientDTO typeRelationshipWithPatientDTO,
                                             BindingResult bindingResult) {

        typeRelationshipWithPatientDTO.setId(typeRelationshipWithPatientId);
        TypeRelationshipWithPatient typeRelationshipWithPatient = convertToTypeRelationship(typeRelationshipWithPatientDTO);

        typeRelationshipIdValidator.validate(typeRelationshipWithPatient, bindingResult);
        typeRelationshipValidator.validate(typeRelationshipWithPatient, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeRelationshipWithPatientNotCreatedOrUpdatedException.class);

        typeRelationshipService.update(typeRelationshipWithPatient);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод обработчик исключения TypeRelationshipWithPatientNotFoundException
    @ExceptionHandler
    private ResponseEntity<TypeRelationshipWithPatientErrorResponse> handleExceptionPriceNotFound(TypeRelationshipWithPatientNotFoundException e) {
        TypeRelationshipWithPatientErrorResponse response = new TypeRelationshipWithPatientErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод обработчик исключения TypeRelationshipWithPatientNotCreatedOrUpdatedException
    @ExceptionHandler
    private ResponseEntity<TypeRelationshipWithPatientErrorResponse> handleExceptionPriceNotCreated(TypeRelationshipWithPatientNotCreatedOrUpdatedException e) {
        TypeRelationshipWithPatientErrorResponse response = new TypeRelationshipWithPatientErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод конверсии из DTO в модель
    private TypeRelationshipWithPatient convertToTypeRelationship(TypeRelationshipWithPatientDTO dto) {
        return this.modelMapper.map(dto, TypeRelationshipWithPatient.class);
    }

    // Метод конверсии из модели в DTO
    private TypeRelationshipWithPatientDTO convertToTypeRelationshipDTO(TypeRelationshipWithPatient relationship) {
        return this.modelMapper.map(relationship, TypeRelationshipWithPatientDTO.class);
    }
}

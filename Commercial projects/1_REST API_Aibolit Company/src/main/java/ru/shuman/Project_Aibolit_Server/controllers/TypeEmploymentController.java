package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.TypeEmploymentDTO;
import ru.shuman.Project_Aibolit_Server.models.TypeEmployment;
import ru.shuman.Project_Aibolit_Server.services.TypeEmploymentService;
import ru.shuman.Project_Aibolit_Server.util.errors.TypeEmploymentErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeEmploymentNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeEmploymentNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeEmploymentIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeEmploymentValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/typesEmployments")
public class TypeEmploymentController {

    private final TypeEmploymentService typeEmploymentService;
    private final TypeEmploymentIdValidator typeEmploymentIdValidator;
    private final TypeEmploymentValidator typeEmploymentValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeEmploymentController(TypeEmploymentService typeEmploymentService,
                                    TypeEmploymentIdValidator typeEmploymentIdValidator,
                                    TypeEmploymentValidator typeEmploymentValidator, ModelMapper modelMapper) {
        this.typeEmploymentService = typeEmploymentService;
        this.typeEmploymentIdValidator = typeEmploymentIdValidator;
        this.typeEmploymentValidator = typeEmploymentValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список типов занятости в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<TypeEmploymentDTO>> sendListTypesEmployments() {

        List<TypeEmployment> typeEmployments = typeEmploymentService.findAll();

        List<TypeEmploymentDTO> typeEmploymentDTOList = typeEmployments.stream()
                .map(this::convertToTypeEmploymentDTO).collect(Collectors.toList());

        return new ResponseEntity<>(typeEmploymentDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один тип занятости по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа TypeEmployment, устанавливаем в нем переданный id, далее валидируем id,
    находим тип занятости и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeEmploymentDTO> sendOneTypeEmployment(@PathVariable(value = "id") int typeEmploymentId,
                                                                   @ModelAttribute(value = "typeEmployment") TypeEmployment typeEmployment,
                                                                   BindingResult bindingResult) {

        typeEmployment.setId(typeEmploymentId);

        typeEmploymentIdValidator.validate(typeEmployment, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeEmploymentNotFoundException.class);

        TypeEmploymentDTO typeEmploymentDTO = convertToTypeEmploymentDTO(typeEmploymentService.findById(typeEmploymentId).get());

        return new ResponseEntity<>(typeEmploymentDTO, HttpStatus.OK);
    }

    /*
    Метод создает новый тип занятости, на вход поступает объект TypeEmploymentDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый тип занятости, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TypeEmploymentDTO typeEmploymentDTO,
                                             BindingResult bindingResult) {

        TypeEmployment typeEmployment = convertToTypeEmployment(typeEmploymentDTO);

        typeEmploymentValidator.validate(typeEmployment, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeEmploymentNotCreatedOrUpdatedException.class);

        typeEmploymentService.create(typeEmployment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий тип занятости, в URL передается id и в виде json объект TypeEmploymentDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int typeEmploymentId,
                                             @RequestBody @Valid TypeEmploymentDTO typeEmploymentDTO,
                                             BindingResult bindingResult) {
        typeEmploymentDTO.setId(typeEmploymentId);
        TypeEmployment typeEmployment = convertToTypeEmployment(typeEmploymentDTO);

        typeEmploymentIdValidator.validate(typeEmployment, bindingResult);
        typeEmploymentValidator.validate(typeEmployment, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeEmploymentNotCreatedOrUpdatedException.class);

        typeEmploymentService.update(typeEmployment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения TypeEmploymentNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<TypeEmploymentErrorResponse> handleExceptionPriceNotFound(TypeEmploymentNotFoundException e) {
        TypeEmploymentErrorResponse response = new TypeEmploymentErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
     Метод обработчик исключения TypeEmploymentNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<TypeEmploymentErrorResponse> handleExceptionPriceNotCreated(TypeEmploymentNotCreatedOrUpdatedException e) {
        TypeEmploymentErrorResponse response = new TypeEmploymentErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private TypeEmployment convertToTypeEmployment(TypeEmploymentDTO typeEmploymentDTO) {
        return this.modelMapper.map(typeEmploymentDTO, TypeEmployment.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private TypeEmploymentDTO convertToTypeEmploymentDTO(TypeEmployment typeEmployment) {
        return this.modelMapper.map(typeEmployment, TypeEmploymentDTO.class);
    }
}

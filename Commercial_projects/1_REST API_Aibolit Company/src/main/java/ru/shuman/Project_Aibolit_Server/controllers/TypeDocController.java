package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.TypeDocDTO;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;
import ru.shuman.Project_Aibolit_Server.services.TypeDocService;
import ru.shuman.Project_Aibolit_Server.util.errors.TypeDocErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeDocNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeDocNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeDocIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeDocValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/typesDocs")
public class TypeDocController {

    private final TypeDocService typeDocService;
    private final TypeDocIdValidator typeDocIdValidator;
    private final TypeDocValidator typeDocValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeDocController(TypeDocService typeDocService, TypeDocIdValidator typeDocIdValidator,
                             TypeDocValidator typeDocValidator, ModelMapper modelMapper) {
        this.typeDocService = typeDocService;
        this.typeDocIdValidator = typeDocIdValidator;
        this.typeDocValidator = typeDocValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список типов документов в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<TypeDocDTO>> sendListTypesDocs() {

        List<TypeDoc> typeDocs = typeDocService.findAll();

        List<TypeDocDTO> typeDocDTOList = typeDocs.stream().map(this::convertToTypeDocDTO).collect(Collectors.toList());

        return new ResponseEntity<>(typeDocDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один тип документа по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа TypeDoc, устанавливаем в нем переданный id, далее валидируем id,
    находим тип документа и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeDocDTO> sendOneTypeDoc(@PathVariable(value = "id") int typeDocId,
                                                     @ModelAttribute(value = "typeDoc") TypeDoc typeDoc,
                                                     BindingResult bindingResult) {

        typeDoc.setId(typeDocId);

        typeDocIdValidator.validate(typeDoc, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeDocNotFoundException.class);

        TypeDocDTO typeDocDTO = convertToTypeDocDTO(typeDocService.findById(typeDocId).get());

        return new ResponseEntity<>(typeDocDTO, HttpStatus.OK);

    }

    /*
    Метод создает новый тип документа, на вход поступает объект TypeDocDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый тип документа, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TypeDocDTO typeDocDTO,
                                             BindingResult bindingResult) {

        TypeDoc typeDoc = convertToTypeDoc(typeDocDTO);

        typeDocValidator.validate(typeDoc, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeDocNotCreatedOrUpdatedException.class);

        typeDocService.create(typeDoc);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий тип документа, в URL передается id и в виде json объект TypeDocDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int typeDocId,
                                             @RequestBody @Valid TypeDocDTO typeDocDTO,
                                             BindingResult bindingResult) {
        typeDocDTO.setId(typeDocId);
        TypeDoc typeDoc = convertToTypeDoc(typeDocDTO);

        typeDocIdValidator.validate(typeDoc, bindingResult);
        typeDocValidator.validate(typeDoc, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeDocNotCreatedOrUpdatedException.class);

        typeDocService.update(typeDoc);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения TypeDocNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<TypeDocErrorResponse> handleException(TypeDocNotFoundException e) {
        TypeDocErrorResponse response = new TypeDocErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения TypeDocCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<TypeDocErrorResponse> handleException(TypeDocNotCreatedOrUpdatedException e) {
        TypeDocErrorResponse response = new TypeDocErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private TypeDoc convertToTypeDoc(TypeDocDTO typeDocDTO) {
        return this.modelMapper.map(typeDocDTO, TypeDoc.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private TypeDocDTO convertToTypeDocDTO(TypeDoc typeDoc) {
        return this.modelMapper.map(typeDoc, TypeDocDTO.class);
    }
}

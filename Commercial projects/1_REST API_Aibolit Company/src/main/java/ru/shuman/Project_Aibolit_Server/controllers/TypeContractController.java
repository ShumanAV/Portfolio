package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.TypeContractDTO;
import ru.shuman.Project_Aibolit_Server.models.TypeContract;
import ru.shuman.Project_Aibolit_Server.services.TypeContractService;
import ru.shuman.Project_Aibolit_Server.util.errors.TypeContractErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeContractNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.TypeContractNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeContractIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeContractValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/typesContracts")
public class TypeContractController {

    private final TypeContractService typeContractService;
    private final TypeContractIdValidator typeContractIdValidator;
    private final TypeContractValidator typeContractValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeContractController(TypeContractService typeContractService, TypeContractIdValidator typeContractIdValidator,
                                  TypeContractValidator typeContractValidator, ModelMapper modelMapper) {
        this.typeContractService = typeContractService;
        this.typeContractIdValidator = typeContractIdValidator;
        this.typeContractValidator = typeContractValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список типов договоров в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<TypeContractDTO>> sendListTypesContracts() {

        List<TypeContract> typeContracts = typeContractService.findAll();

        List<TypeContractDTO> typeContractDTOList = typeContracts.stream().map(this::convertToTypeContractDTO).collect(Collectors.toList());

        return new ResponseEntity<>(typeContractDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один тип договора по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа TypeContract, устанавливаем в нем переданный id, далее валидируем id,
    находим тип договора и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeContractDTO> sendOneTypeContract(@PathVariable(value = "id") int typeContractId,
                                                               @ModelAttribute(value = "typeContract") TypeContract typeContract,
                                                               BindingResult bindingResult) {

        typeContract.setId(typeContractId);

        typeContractIdValidator.validate(typeContract, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeContractNotFoundException.class);

        TypeContractDTO typeContractDTO = convertToTypeContractDTO(typeContractService.findById(typeContractId).get());

        return new ResponseEntity<>(typeContractDTO, HttpStatus.OK);

    }

    /*
    Метод создает новый тип договора, на вход поступает объект TypeContractDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый тип договора, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TypeContractDTO typeContractDTO,
                                             BindingResult bindingResult) {

        TypeContract typeContract = convertToTypeContract(typeContractDTO);

        typeContractValidator.validate(typeContract, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeContractNotCreatedOrUpdatedException.class);

        typeContractService.create(typeContract);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий тип договора, в URL передается id и в виде json объект TypeContractDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int typeContractId,
                                             @RequestBody @Valid TypeContractDTO typeContractDTO,
                                             BindingResult bindingResult) {
        typeContractDTO.setId(typeContractId);
        TypeContract typeContract = convertToTypeContract(typeContractDTO);

        typeContractIdValidator.validate(typeContract, bindingResult);
        typeContractValidator.validate(typeContract, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, TypeContractNotCreatedOrUpdatedException.class);

        typeContractService.update(typeContract);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения TypeContractNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<TypeContractErrorResponse> handleException(TypeContractNotFoundException e) {
        TypeContractErrorResponse response = new TypeContractErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения TypeContractNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<TypeContractErrorResponse> handleException(TypeContractNotCreatedOrUpdatedException e) {
        TypeContractErrorResponse response = new TypeContractErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private TypeContract convertToTypeContract(TypeContractDTO typeContractDTO) {
        return this.modelMapper.map(typeContractDTO, TypeContract.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private TypeContractDTO convertToTypeContractDTO(TypeContract typeContract) {
        return this.modelMapper.map(typeContract, TypeContractDTO.class);
    }
}

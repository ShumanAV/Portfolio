package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.BloodDTO;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.services.BloodService;
import ru.shuman.Project_Aibolit_Server.util.errors.BloodErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.BloodNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.BloodNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.BloodIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.BloodValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/bloods")
public class BloodController {

    private final BloodService bloodService;
    private final BloodIdValidator bloodIdValidator;
    private final BloodValidator bloodValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public BloodController(BloodService bloodService, BloodIdValidator bloodIdValidator, BloodValidator bloodValidator,
                           ModelMapper modelMapper) {
        this.bloodService = bloodService;
        this.bloodIdValidator = bloodIdValidator;
        this.bloodValidator = bloodValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список групп крови в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<BloodDTO>> sendListBloods() {

        List<Blood> bloodList = bloodService.findAll();

        List<BloodDTO> bloodDTOList = bloodList.stream().map(this::convertToBloodDTO).collect(Collectors.toList());

        return new ResponseEntity<>(bloodDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает одну группу крови по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа Blood, устанавливаем в нем переданный id, далее валидируем id,
    находим группу крови и возвращаем ее
     */
    @GetMapping("/{id}")
    public ResponseEntity<BloodDTO> sendOneBlood(@PathVariable(value = "id") int bloodId,
                                                 @ModelAttribute(value = "blood") Blood blood,
                                                 BindingResult bindingResult) {

        blood.setId(bloodId);

        bloodIdValidator.validate(blood, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, BloodNotFoundException.class);

        BloodDTO bloodDTO = convertToBloodDTO(bloodService.findById(bloodId).get());

        return new ResponseEntity<>(bloodDTO, HttpStatus.OK);

    }

    /*
    Метод создает новую группу крови, на вход поступает объект BloodDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новую группу крови, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BloodDTO bloodDTO,
                                             BindingResult bindingResult) {

        Blood blood = convertToBlood(bloodDTO);

        bloodValidator.validate(blood, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, BloodNotCreatedOrUpdatedException.class);

        bloodService.create(blood);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующую группу крови, в URL передается id и в виде json объект BloodDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int bloodId,
                                             @RequestBody @Valid BloodDTO bloodDTO,
                                             BindingResult bindingResult) {

        bloodDTO.setId(bloodId);
        Blood blood = convertToBlood(bloodDTO);

        bloodIdValidator.validate(blood, bindingResult);
        bloodValidator.validate(blood, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, BloodNotCreatedOrUpdatedException.class);

        bloodService.update(blood);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения BloodNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<BloodErrorResponse> handleException(BloodNotFoundException e) {
        BloodErrorResponse response = new BloodErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения BloodNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<BloodErrorResponse> handleException(BloodNotCreatedOrUpdatedException e) {
        BloodErrorResponse response = new BloodErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private Blood convertToBlood(BloodDTO bloodDTO) {
        return this.modelMapper.map(bloodDTO, Blood.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private BloodDTO convertToBloodDTO(Blood blood) {
        return this.modelMapper.map(blood, BloodDTO.class);
    }
}

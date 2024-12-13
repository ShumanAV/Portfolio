package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.GenderDTO;
import ru.shuman.Project_Aibolit_Server.models.Gender;
import ru.shuman.Project_Aibolit_Server.services.GenderService;
import ru.shuman.Project_Aibolit_Server.util.errors.GenderErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.GenderNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.GenderNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.GenderIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.GenderValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/genders")
public class GenderController {

    private final GenderService genderService;
    private final GenderIdValidator genderIdValidator;
    private final GenderValidator genderValidator;
    private final ModelMapper modelMapper;

    /*
    Внедряем зависимости
     */
    @Autowired
    public GenderController(GenderService genderService, GenderIdValidator genderIdValidator,
                            GenderValidator genderValidator, ModelMapper modelMapper) {
        this.genderService = genderService;
        this.genderIdValidator = genderIdValidator;
        this.genderValidator = genderValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список гендеров в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<GenderDTO>> sendListGenders() {

        List<Gender> genders = genderService.findAll();

        List<GenderDTO> genderDTOList = genders.stream().map(this::convertToGenderDTO).collect(Collectors.toList());

        return new ResponseEntity<>(genderDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает гендер по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа Gender, устанавливаем в нем переданный id, далее валидируем id,
    находим гендер и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenderDTO> sendOneGender(@PathVariable(value = "id") int genderId,
                                                   @ModelAttribute(value = "gender") Gender gender,
                                                   BindingResult bindingResult) {

        gender.setId(genderId);

        genderIdValidator.validate(gender, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, GenderNotFoundException.class);

        GenderDTO genderDTO = convertToGenderDTO(genderService.findById(genderId).get());

        return new ResponseEntity<>(genderDTO, HttpStatus.OK);

    }

    /*
    Метод создает новый гендер, на вход поступает объект GenderDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый гендер, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid GenderDTO genderDTO,
                                             BindingResult bindingResult) {

        Gender gender = convertToGender(genderDTO);

        genderValidator.validate(gender, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, GenderNotCreatedOrUpdatedException.class);

        genderService.create(gender);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий гендер, в URL передается id и в виде json объект GenderDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int genderId,
                                             @RequestBody @Valid GenderDTO genderDTO,
                                             BindingResult bindingResult) {
        genderDTO.setId(genderId);
        Gender gender = convertToGender(genderDTO);

        genderIdValidator.validate(gender, bindingResult);
        genderValidator.validate(gender, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, GenderNotCreatedOrUpdatedException.class);

        genderService.update(gender);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения GenderNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<GenderErrorResponse> handleException(GenderNotFoundException e) {
        GenderErrorResponse response = new GenderErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения GenderNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<GenderErrorResponse> handleException(GenderNotCreatedOrUpdatedException e) {
        GenderErrorResponse response = new GenderErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private Gender convertToGender(GenderDTO genderDTO) {
        return this.modelMapper.map(genderDTO, Gender.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private GenderDTO convertToGenderDTO(Gender gender) {
        return this.modelMapper.map(gender, GenderDTO.class);
    }
}

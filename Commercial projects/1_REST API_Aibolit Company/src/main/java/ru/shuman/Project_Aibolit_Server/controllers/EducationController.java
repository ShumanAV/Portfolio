package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.EducationDTO;
import ru.shuman.Project_Aibolit_Server.models.Education;
import ru.shuman.Project_Aibolit_Server.services.EducationService;
import ru.shuman.Project_Aibolit_Server.util.errors.EducationErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.EducationNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.EducationNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.EducationIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.EducationValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/educations")
public class EducationController {

    private final EducationService educationService;
    private final EducationIdValidator educationIdValidator;
    private final EducationValidator educationValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public EducationController(EducationService educationService, EducationIdValidator educationIdValidator,
                               EducationValidator educationValidator, ModelMapper modelMapper) {
        this.educationService = educationService;
        this.educationIdValidator = educationIdValidator;
        this.educationValidator = educationValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список типов образований в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<EducationDTO>> sendListEducations() {

        List<Education> educations = educationService.findAll();

        List<EducationDTO> educationDTOList = educations.stream().map(this::convertToEducationDTO).collect(Collectors.toList());

        return new ResponseEntity<>(educationDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один тип образования по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа Education, устанавливаем в нем переданный id, далее валидируем id,
    находим тип образования и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<EducationDTO> sendOneEducation(@PathVariable(value = "id") int educationId,
                                                     @ModelAttribute(value = "education") Education education,
                                                     BindingResult bindingResult) {

        education.setId(educationId);

        educationIdValidator.validate(education, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, EducationNotFoundException.class);

        EducationDTO educationDTO = convertToEducationDTO(educationService.findById(educationId).get());

        return new ResponseEntity<>(educationDTO, HttpStatus.OK);

    }

    /*
    Метод создает новый тип образования, на вход поступает объект EducationDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый тип образования, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid EducationDTO educationDTO,
                                             BindingResult bindingResult) {

        Education education = convertToEducation(educationDTO);

        educationValidator.validate(education, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, EducationNotCreatedOrUpdatedException.class);

        educationService.create(education);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий тип образования, в URL передается id и в виде json объект EducationDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int educationId,
                                             @RequestBody @Valid EducationDTO educationDTO,
                                             BindingResult bindingResult) {

        educationDTO.setId(educationId);
        Education education = convertToEducation(educationDTO);

        educationIdValidator.validate(education, bindingResult);
        educationValidator.validate(education, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, EducationNotCreatedOrUpdatedException.class);

        educationService.update(education);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод обработчик исключения EducationNotFoundException
    @ExceptionHandler
    private ResponseEntity<EducationErrorResponse> handleException(EducationNotFoundException e) {
        EducationErrorResponse response = new EducationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод обработчик исключения EducationNotCreatedOrUpdatedException
    @ExceptionHandler
    private ResponseEntity<EducationErrorResponse> handleException(EducationNotCreatedOrUpdatedException e) {
        EducationErrorResponse response = new EducationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод конверсии из DTO в модель
    private Education convertToEducation(EducationDTO educationDTO) {
        return this.modelMapper.map(educationDTO, Education.class);
    }

    // Метод конверсии из модели в DTO
    private EducationDTO convertToEducationDTO(Education education) {
        return this.modelMapper.map(education, EducationDTO.class);
    }
}

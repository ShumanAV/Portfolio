package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.SpecializationDTO;
import ru.shuman.Project_Aibolit_Server.models.Specialization;
import ru.shuman.Project_Aibolit_Server.services.SpecializationService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;
import ru.shuman.Project_Aibolit_Server.util.errors.SpecializationErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.SpecializationNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.SpecializationNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.SpecializationIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.SpecializationValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;
    private final SpecializationIdValidator specializationIdValidator;
    private final SpecializationValidator specializationValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SpecializationController(SpecializationService specializationService,
                                    SpecializationIdValidator specializationIdValidator,
                                    SpecializationValidator specializationValidator, ModelMapper modelMapper) {
        this.specializationService = specializationService;
        this.specializationIdValidator = specializationIdValidator;
        this.specializationValidator = specializationValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<SpecializationDTO>> sendListSpecializations(@RequestParam(value = "published", required = false) Boolean published) {

        List<Specialization> specializations;
        if (published == null) {
            specializations = specializationService.findAll();
        } else {
            specializations = specializationService.findAllByPublished(published);
        }

        List<SpecializationDTO> specializationDTOList = specializations.stream().map(this::convertToSpecializationDTO).
                collect(Collectors.toList());

        return new ResponseEntity<>(specializationDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationDTO> sendOneSpecialization(@PathVariable(value = "id") int specializationId,
                                                                   @ModelAttribute(value = "specialization") Specialization specialization,
                                                                   BindingResult bindingResult) {

        specialization.setId(specializationId);

        specializationIdValidator.validate(specialization, bindingResult);

        GeneralMethods.collectStringAboutErrors(bindingResult, SpecializationNotFoundException.class);

        SpecializationDTO specializationDTO = convertToSpecializationDTO(specializationService.findById(specializationId).get());

        return new ResponseEntity<>(specializationDTO, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SpecializationDTO specializationDTO,
                                             BindingResult bindingResult) {

        Specialization specialization = convertToSpecialization(specializationDTO);

        specializationValidator.validate(specialization, bindingResult);

        GeneralMethods.collectStringAboutErrors(bindingResult, SpecializationNotCreatedOrUpdatedException.class);

        specializationService.create(specialization);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int specializationId,
                                             @RequestBody @Valid SpecializationDTO specializationDTO,
                                             BindingResult bindingResult) {

        Specialization specialization = convertToSpecialization(specializationDTO);

        specializationIdValidator.validate(specialization, bindingResult);
        specializationValidator.validate(specialization, bindingResult);

        GeneralMethods.collectStringAboutErrors(bindingResult, SpecializationNotCreatedOrUpdatedException.class);

        specializationService.update(specialization);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод обработчик исключения SpecializationNotFound
    @ExceptionHandler
    private ResponseEntity<SpecializationErrorResponse> handleExceptionSpecializationNotFound(SpecializationNotFoundException e) {
        SpecializationErrorResponse response = new SpecializationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод обработчик исключения SpecializationNotCreatedOrUpdatedException
    @ExceptionHandler
    private ResponseEntity<SpecializationErrorResponse> handleExceptionSpecializationNotCreatedException(SpecializationNotCreatedOrUpdatedException e) {
        SpecializationErrorResponse response = new SpecializationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод конверсии из DTO в модель
    public Specialization convertToSpecialization(SpecializationDTO specializationDTO) {
        return this.modelMapper.map(specializationDTO, Specialization.class);
    }

    // Метод конверсии из модели в DTO
    public SpecializationDTO convertToSpecializationDTO(Specialization specialization) {
        return this.modelMapper.map(specialization, SpecializationDTO.class);
    }
}

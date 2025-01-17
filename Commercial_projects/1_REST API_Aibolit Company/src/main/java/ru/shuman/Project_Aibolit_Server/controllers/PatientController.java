package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.PatientDTO;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.services.PatientService;
import ru.shuman.Project_Aibolit_Server.util.errors.PatientErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.PatientNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.PatientNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.PatientIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.PatientValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientValidator patientValidator;
    private final PatientIdValidator patientIdValidator;
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PatientController(PatientValidator patientValidator, PatientIdValidator patientIdValidator,
                             PatientService patientService, ModelMapper modelMapper) {
        this.patientValidator = patientValidator;
        this.patientIdValidator = patientIdValidator;
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    /*
    Метод возвращает список пациентов в обертке ResponseEntity, в URL может быть параметр запроса published,
    список формируется с учетом этого
     */
    @GetMapping
    public ResponseEntity<List<PatientDTO>> sendListPatients(@RequestParam(value = "published", required = false) Boolean published) {

        List<Patient> patients;
        if (published == null) {
            patients = patientService.findAll();
        } else {
            patients = patientService.findAllByPublished(published);
        }

        List<PatientDTO> patientDTOList = patients.stream().map(this::convertToPatientDTO).collect(Collectors.toList());

        return new ResponseEntity<>(patientDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает одного пациента по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа Patient, устанавливаем в нем переданный id, далее валидируем id,
    находим пациента и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> sendOnePatient(@PathVariable(value = "id") int patientId,
                                                     @ModelAttribute(value = "patient") Patient patient,
                                                     BindingResult bindingResult) {

        patient.setId(patientId);

        patientIdValidator.validate(patient, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, PatientNotFoundException.class);

        PatientDTO patientDTO = convertToPatientDTO(patientService.findById(patientId).get());

        return new ResponseEntity<>(patientDTO, HttpStatus.OK);

    }

    /*
    Метод создает нового пациента, на вход поступает объект PatientDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем нового пациента, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PatientDTO patientDTO,
                                             BindingResult bindingResult) {

        Patient patient = convertToPatient(patientDTO);

        patientValidator.validate(patient, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, PatientNotCreatedOrUpdatedException.class);

        patientService.create(patient);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующего пациента, в URL передается id и в виде json объект PatientDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int patientId,
                                             @RequestBody @Valid PatientDTO patientDTO,
                                             BindingResult bindingResult) {

        patientDTO.setId(patientId);
        Patient patient = convertToPatient(patientDTO);

        patientIdValidator.validate(patient, bindingResult);
        patientValidator.validate(patient, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, PatientNotCreatedOrUpdatedException.class);

        patientService.update(patient);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод ищет пациентов по строке текста, строка текста передается в виде параметра запроса,
    возвращает список найденных пациентов в обертке ResponseEntity
     */
    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> search(@RequestParam(value = "text_search", required = true) String textSearch) {

        List<Patient> patients = patientService.search(textSearch);

        List<PatientDTO> patientDTOList = patients.stream().map(this::convertToPatientDTO).collect(Collectors.toList());

        return new ResponseEntity<>(patientDTOList, HttpStatus.OK);

    }

    /*
    Метод обработчик исключения PatientNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<PatientErrorResponse> handleException(PatientNotFoundException e) {
        PatientErrorResponse response = new PatientErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения PatientNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<PatientErrorResponse> handleException(PatientNotCreatedOrUpdatedException e) {
        PatientErrorResponse response = new PatientErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private Patient convertToPatient(PatientDTO patientDTO) {
        return this.modelMapper.map(patientDTO, Patient.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private PatientDTO convertToPatientDTO(Patient patient) {
        return this.modelMapper.map(patient, PatientDTO.class);
    }
}

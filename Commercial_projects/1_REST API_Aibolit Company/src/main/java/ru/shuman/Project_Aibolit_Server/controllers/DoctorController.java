package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.DoctorDTO;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.services.DoctorService;
import ru.shuman.Project_Aibolit_Server.util.errors.DoctorErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.DoctorNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ProfileOrDoctorNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.validators.DoctorIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.DoctorValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;
    private final DoctorValidator doctorValidator;
    private final DoctorIdValidator doctorIdValidator;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public DoctorController(DoctorService doctorService, ModelMapper modelMapper, DoctorValidator doctorValidator,
                            DoctorIdValidator doctorIdValidator) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
        this.doctorValidator = doctorValidator;
        this.doctorIdValidator = doctorIdValidator;
    }

    /*
        Метод возвращает список докторов.

        Метод принимает два параметра запроса:
        published и show_in_schedule, если они есть, и далее,
        в зависимости от условия есть ли данные параметры или один из них возвращает список докторов:
        - просто список докторов без условий
        - список докторов с published = true или false
        - список докторов с show_in_schedule = true или false
     */
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> sendListDoctors(@RequestParam(value = "published", required = false) Boolean published,
                                                           @RequestParam(value = "show_in_schedule", required = false) Boolean showInSchedule) {

        List<Doctor> doctors = new ArrayList<>();
        if (published == null && showInSchedule == null) {
            doctors = doctorService.findAll();
        } else if (published != null && showInSchedule == null) {
            doctors = doctorService.findAllByPublished(published);
        } else if (published == null && showInSchedule != null) {
            doctors = doctorService.findAllByShowInSchedule(showInSchedule);
        } else if (published != null && showInSchedule != null) {
            doctors = doctorService.findAllByPublishedAndShowInSchedule(published, showInSchedule);
        }

        List<DoctorDTO> doctorDTOList = doctors.stream().map(this::convertToDoctorDTO).collect(Collectors.toList());

        return new ResponseEntity<>(doctorDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает одного доктора по id.

    Метод принимает обязательный параметр запроса id доктора,
    создает при помощи @ModelAttribute(value = "doctor") пустой объект типа Doctor, в который помещаем полученный id,
    и возвращает конкретного доктора в обертке ResponseEntity<DoctorDTO>, если для данного id есть врач.

    Перед отправкой врача осуществляется проверка id в валидаторе userValidatorId на предмет того, что для данного id
    есть врач, далее проверяется есть ли ошибки в bindingResult.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> sendOneDoctor(@PathVariable(value = "id") int doctorId,
                                                   @ModelAttribute(value = "doctor") Doctor doctor,
                                                   BindingResult bindingResult) {

        doctor.setId(doctorId);

        doctorIdValidator.validate(doctor, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, DoctorNotFoundException.class);

        DoctorDTO doctorDTO = convertToDoctorDTO(doctorService.findById(doctorId).get());

        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);

    }

    /*
    Метод update выполняет апдейт существующего входящего врача.

    Метод принимает JSON при помощи @RequestBody, в котором лежит объект типа DoctorDTO,
    при приеме объекта осуществляется валидация в соответствии с аннотациями,
    далее валидируется в двух валидаторах doctorValidator и doctorValidatorId, причем валидаторы специально разделены на два,
    для того чтобы проверку валидности id осуществлять в отдельном валидаторе, т.к. с клиента могут поступать новые
    объекты (врачи, пациенты, и т.д.) без id, но их поля нужно валидировать, а если будем валидировать в одном
    валидаторе сразу и id, то возникает проблема т.к. его нет, а также иногда нужно валидировать отдельно
    только id в обязательном порядке.

    Метод осуществляет апдейт существующего врача и возвращает обертку со статусом ResponseEntity<HttpStatus>
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int doctorId,
                                             @RequestBody @Valid DoctorDTO updatedDoctorDTO,
                                             BindingResult bindingResult) {
        updatedDoctorDTO.setId(doctorId);
        Doctor updatedDoctor = convertToDoctor(updatedDoctorDTO);

        doctorIdValidator.validate(updatedDoctor, bindingResult);
        doctorValidator.validate(updatedDoctor, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, ProfileOrDoctorNotCreatedOrUpdatedException.class);

        doctorService.update(updatedDoctor);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения ProfileOrDoctorNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<DoctorErrorResponse> handleException(ProfileOrDoctorNotCreatedOrUpdatedException e) {
        DoctorErrorResponse response = new DoctorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения DoctorNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<DoctorErrorResponse> handleException(DoctorNotFoundException e) {
        DoctorErrorResponse response = new DoctorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /*
    Метод конвертации из DTO в модель
     */
    private Doctor convertToDoctor(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }

    /*
    Метод конвертации из модели в DTO
     */
    private DoctorDTO convertToDoctorDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }
}

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
import ru.shuman.Project_Aibolit_Server.util.validators.DoctorValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.DoctorIdValidator;
import ru.shuman.Project_Aibolit_Server.util.errors.DoctorErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.UserOrDoctorNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.DoctorNotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.collectErrorsToString;

@RestController
@RequestMapping("/users")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;
    private final DoctorValidator doctorValidator;
    private final DoctorIdValidator doctorIdValidator;

    @Autowired
    public DoctorController(DoctorService doctorService, ModelMapper modelMapper, DoctorValidator doctorValidator,
                            DoctorIdValidator doctorIdValidator) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
        this.doctorValidator = doctorValidator;
        this.doctorIdValidator = doctorIdValidator;
    }

    /*
        Метод sendListUsers возвращает список юзеров.

        Метод принимает два параметра запроса:
        published и show_in_schedule, если они есть, и далее,
        в зависимости от условия есть ли данные параметры или один из них возвращает список юзеров:
        - просто список юзеров без условий
        - список юзеров с published = true или false
        - список юзеров с show_in_schedule = true или false
     */

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> sendListUsers(@RequestParam(value = "published", required = false) Boolean published,
                                                         @RequestParam(value = "show_in_schedule", required = false) Boolean showInSchedule) {

        List<Doctor> doctors = new ArrayList<>();
        if (published == null && showInSchedule == null ) {
            doctors = doctorService.findAll();
        } else if (published != null && showInSchedule == null) {
            doctors = doctorService.findAllByPublished(published);
        } else if (published == null && showInSchedule != null) {
            doctors = doctorService.findAllByShowInSchedule(showInSchedule);
        } else if (published != null && showInSchedule != null) {
            doctors = doctorService.findAllByPublishedAndShowInSchedule(published, showInSchedule);
        }

        List<DoctorDTO> doctorDTOList = doctors.stream().map(this::convertToUserDTO).collect(Collectors.toList());

        return new ResponseEntity<>(doctorDTOList, HttpStatus.OK);
    }

    /*
    Метод sendOneUser возвращает одного юзера по id.

    Метод принимает обязательный параметр запроса id юзера,
    создает при помощи @ModelAttribute(value = "user") пустой объект типа User user, в который помещаем полученный id,
    и возвращает конкретного юзера в оболочке ResponseEntity<UserDTO>, если для данного id есть юзер.

    Перед отправкой юзера осуществляется проверка id в валидаторе userValidatorId на предмет того, что для данного id
    есть юзер, далее проверяется есть ли ошибки в bindingResult.
     */

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> sendOneUser(@PathVariable(value = "id") int userId,
                                                 @ModelAttribute(value = "user") Doctor doctor,
                                                 BindingResult bindingResult) {

        doctor.setId(userId);

        doctorIdValidator.validate(doctor, bindingResult);

        collectErrorsToString(bindingResult, DoctorNotFoundException.class);

        DoctorDTO doctorDTO = convertToUserDTO(doctorService.findById(userId).get());

        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);

    }

    /*
    Метод update выполняет апдейт существующего входящего юзера.

    Метод принимает JSON при помощи @RequestBody, в котором лежит объект типа UserDTO userDTO,
    при приеме объекта UserDTO userDTO осуществляется валидация в соответствии с аннотациями в UserDTO,
    далее валидируется в двух валидаторах userValidator и userValidatorId, причем валидаторы специально разделены на два,
    для того чтобы проверку валидности id осуществлять в отдельном валидаторе, т.к. с клиента могут поступать новые
    объекты (юзеры, пациенты, и т.д.) без id, но их поля нужно валидировать, а также иногда нужно валидировать отдельно
    только id в обязательном порядке.

    Метод осуществляет апдейт существующего юзера и возвращает обертку со статусом ResponseEntity<HttpStatus>
     */

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid DoctorDTO updatedDoctorDTO,
                                             BindingResult bindingResult) {

        Doctor updatedDoctor = convertToUser(updatedDoctorDTO);

        doctorValidator.validate(updatedDoctor, bindingResult);
        doctorIdValidator.validate(updatedDoctor, bindingResult);

        collectErrorsToString(bindingResult, UserOrDoctorNotCreatedOrUpdatedException.class);

        doctorService.update(updatedDoctor);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод обработчик исключения UserOrDoctorNotCreatedOrUpdatedException
    @ExceptionHandler
    private ResponseEntity<DoctorErrorResponse> handleException(UserOrDoctorNotCreatedOrUpdatedException e) {
        DoctorErrorResponse response = new DoctorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод обработчик исключения UserNotFound
    @ExceptionHandler
    private ResponseEntity<DoctorErrorResponse> handleException(DoctorNotFoundException e) {
        DoctorErrorResponse response = new DoctorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Метод конвертации из DTO в модель
    private Doctor convertToUser(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }

    // Метод конвертации из модели в DTO
    private DoctorDTO convertToUserDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }
}

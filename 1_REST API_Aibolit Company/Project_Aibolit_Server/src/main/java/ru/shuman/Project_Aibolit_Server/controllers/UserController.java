package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.UserDTO;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.services.UserService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;
import ru.shuman.Project_Aibolit_Server.util.validators.UserValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.UserIdValidator;
import ru.shuman.Project_Aibolit_Server.util.errors.UserErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ProfileOrUserNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.UserNotFound;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;
    private final UserIdValidator userIdValidator;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserValidator userValidator,
                          UserIdValidator userIdValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
        this.userIdValidator = userIdValidator;
    }

    /*
        Метод public ResponseEntity<List<UserDTO>> sendListUsers возвращает список юзеров.

        Метод принимает два параметра запроса:
        published и show_in_schedule, если они есть, и далее,
        в зависимости от условия есть ли данные параметры или один из них возвращает список юзеров:
        - просто список юзеров без условий
        - список юзеров с published = true или false
        - список юзеров с show_in_schedule = true или false
     */

    @GetMapping
    public ResponseEntity<List<UserDTO>> sendListUsers(@RequestParam(value = "published", required = false) Boolean published,
                                                       @RequestParam(value = "show_in_schedule", required = false) Boolean showInSchedule) {

        List<User> users = new ArrayList<>();
        if (published == null && showInSchedule == null ) {
            users = userService.findAll();
        } else if (published != null && showInSchedule == null) {
            users = userService.findAllByPublished(published);
        } else if (published == null && showInSchedule != null) {
            users = userService.findAllByShowInSchedule(showInSchedule);
        } else if (published != null && showInSchedule != null) {
            users = userService.findAllByPublishedAndShowInSchedule(published, showInSchedule);
        }

        List<UserDTO> userDTOList = users.stream().map(this::convertToUserDTO).collect(Collectors.toList());

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /*
    Метод public ResponseEntity<UserDTO> sendOneUser возвращает одного юзера по id.

    Метод принимает обязательный параметр запроса id юзера,
    создает при помощи @ModelAttribute(value = "user") пустой объект типа User user, в который помещаем полученный id,
    и возвращает конкретного юзера в оболочке ResponseEntity<UserDTO>, если для данного id есть юзер.

    Перед отправкой юзера осуществляется проверка id в валидаторе userValidatorId на предмет того, что для данного id
    есть юзер, далее проверяется есть ли ошибки в bindingResult.
     */

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> sendOneUser(@PathVariable(value = "id") int userId,
                                               @ModelAttribute(value = "user") User user,
                                               BindingResult bindingResult) {

        user.setId(userId);

        userIdValidator.validate(user, bindingResult);

        StandardMethods.collectStringAboutErrors(bindingResult, UserNotFound.class);

        UserDTO userDTO = convertToUserDTO(userService.findById(userId).get());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    /*
    Метод public ResponseEntity<HttpStatus> update выполняет апдейт существующего входящего юзера.

    Метод принимает JSON при помощи @RequestBody, в котором лежит объект типа UserDTO userDTO,
    при приеме объекта UserDTO userDTO осуществляется валидация в соответствии с аннотациями в UserDTO,
    далее валидируется в двух валидаторах userValidator и userValidatorId, причем валидаторы специально разделены на два,
    для того чтобы проверку валидности id осуществялть в отдельном валидаторе, чтобы это не влияло на проверку
    заполненного юзера.

    Метод осуществляет апдейт существующего юзера и возвращает обертку со статусом ResponseEntity<HttpStatus>
     */

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult) {

        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);
        userIdValidator.validate(user, bindingResult);

        StandardMethods.collectStringAboutErrors(bindingResult, ProfileOrUserNotCreatedOrUpdatedException.class);

        userService.update(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод обработчик исключения ProfileOrUserNotCreatedOrUpdatedException
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleExceptionProfileOrUserNotCreated(ProfileOrUserNotCreatedOrUpdatedException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод обработчик исключения UserNotFound
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFound e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Метод конвертации из DTO в модель
    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    // Метод конвертации из модели в DTO
    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}

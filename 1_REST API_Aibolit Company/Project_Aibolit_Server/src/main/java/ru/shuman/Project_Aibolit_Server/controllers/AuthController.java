package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.AuthenticationDTO;
import ru.shuman.Project_Aibolit_Server.dto.UserDTO;
import ru.shuman.Project_Aibolit_Server.models.User;
import ru.shuman.Project_Aibolit_Server.security.JWTUtil;
import ru.shuman.Project_Aibolit_Server.services.UserService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;
import ru.shuman.Project_Aibolit_Server.util.errors.AuthenticationErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ProfileNotAuthenticatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ProfileOrUserNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.validators.UserValidator;
import ru.shuman.Project_Aibolit_Server.util.errors.UserErrorResponse;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, UserValidator userValidator, ModelMapper modelMapper,
                          JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /*
    Метод public ResponseEntity<HttpStatus> performRegistration выполняет регистрацию юзера,
    если у входящего юзера есть id, выполняется апдейт существующего юзера и профайла при наличии,
    если id нет, то выполняется создание нового юзера с профайлом при наличии.

    Метод принимает JSON с объектом типа UserDTO userDTO при помощи @RequestBody,
    осуществляет валидацию данных в соответствии с аннотациями в UserDTO.

    Возвращает оболочку со статусом ResponseEntity<HttpStatus>
     */

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                                          BindingResult bindingResult) {

        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);

        StandardMethods.collectStringAboutErrors(bindingResult, ProfileOrUserNotCreatedOrUpdatedException.class);

        userService.register(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод public ResponseEntity<Map<String, String>> performLogin выполняет аутентификацию пользователя.

    Метод принимает JSON с объектом типа AuthenticationDTO authenticationDTO с именем пользователя и паролем.
    В случае неправильного логина и пароля выбрасывает исключение
    throw new ProfileNotAuthenticatedException("Неправильный логин или пароль!").

    В случае прохождения аутентификации формирует новый jwt токен и возвращает его в оболочке со статусом 200
    new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK).
     */

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {

        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(),
                authenticationDTO.getPassword()
        );

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new ProfileNotAuthenticatedException("Неправильный логин или пароль!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());

        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
    }

    // Метод обработчик исключения ProfileNotAuthenticatedException
    @ExceptionHandler
    private ResponseEntity<AuthenticationErrorResponse> handleExceptionProfileNotAuthenticated(ProfileNotAuthenticatedException e) {
        AuthenticationErrorResponse response = new AuthenticationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    // Метод конверсии из DTO в модель
    public User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }

}

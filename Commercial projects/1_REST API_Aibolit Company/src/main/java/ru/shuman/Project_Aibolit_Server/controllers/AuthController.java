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
import ru.shuman.Project_Aibolit_Server.dto.DoctorDTO;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.security.JWTUtil;
import ru.shuman.Project_Aibolit_Server.services.DoctorService;
import ru.shuman.Project_Aibolit_Server.util.errors.AuthenticationErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ProfileNotAuthenticatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.ProfileOrDoctorNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.validators.DoctorValidator;
import ru.shuman.Project_Aibolit_Server.util.errors.DoctorErrorResponse;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final DoctorService doctorService;
    private final DoctorValidator doctorValidator;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public AuthController(DoctorService doctorService, DoctorValidator doctorValidator, ModelMapper modelMapper,
                          JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.doctorService = doctorService;
        this.doctorValidator = doctorValidator;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /*
    Метод performRegistration выполняет регистрацию доктора (он же врач).
    Доктора могут создать нового в клиенте, либо изменить существующего.
    Если у входящего доктора есть id, выполняется апдейт существующего доктора и профиля при наличии доступа в систему,
    если id нет, то выполняется создание нового доктора с профилем (логин и пароль для входа в систему)
    при наличии доступа в систему.

    Метод принимает JSON с объектом типа DoctorDTO doctorDTO при помощи @RequestBody,
    осуществляет валидацию данных в соответствии с аннотациями и валидатором.

    Возвращает обертку со статусом ResponseEntity<HttpStatus>
     */

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid DoctorDTO doctorDTO,
                                                          BindingResult bindingResult) {

        Doctor doctor = convertToDoctor(doctorDTO);

        doctorValidator.validate(doctor, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, ProfileOrDoctorNotCreatedOrUpdatedException.class);

        doctorService.register(doctor);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод performLogin выполняет аутентификацию пользователя.

    Метод принимает JSON с объектом типа AuthenticationDTO authenticationDTO с именем пользователя и паролем.
    В случае неправильного логина и пароля выбрасывает исключение ProfileNotAuthenticatedException

    В случае прохождения аутентификации формирует новый jwt токен и возвращает его в оболочке со статусом 200
    new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK).
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody @Valid AuthenticationDTO authenticationDTO) {

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

        Map<String, String> map = new HashMap<>();
        map.put("jwt-token", token);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /*
    Метод обработчик исключения ProfileNotAuthenticatedException
     */
    @ExceptionHandler
    private ResponseEntity<AuthenticationErrorResponse> handleException(ProfileNotAuthenticatedException e) {
        AuthenticationErrorResponse response = new AuthenticationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
    Метод конверсии из DTO в модель
     */
    private Doctor convertToDoctor(DoctorDTO doctorDTO) {
        return this.modelMapper.map(doctorDTO, Doctor.class);
    }

}

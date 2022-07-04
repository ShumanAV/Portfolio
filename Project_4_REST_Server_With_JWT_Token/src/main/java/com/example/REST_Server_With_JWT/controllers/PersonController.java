package com.example.REST_Server_With_JWT.controllers;

import com.example.REST_Server_With_JWT.dto.PersonDTO;
import com.example.REST_Server_With_JWT.models.Person;
import com.example.REST_Server_With_JWT.security.JWTUtil;
import com.example.REST_Server_With_JWT.services.PersonService;
import com.example.REST_Server_With_JWT.util.exceptions.CreateMessageException;
import com.example.REST_Server_With_JWT.util.validators.PersonValidator;
import com.example.REST_Server_With_JWT.util.exceptions.MessageException;
import com.example.REST_Server_With_JWT.util.responses.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/*
    В данном контроллере обрабатывается POST запрос по url /auth/login, на вход подается json со структурой согласно ТЗ,
    при получении запроса на стороне сервера осуществляется проверка данных, есть ли такой пользователь в БД, не пустое ли имя пользователя,
    не пустой ли пароль, совпадает ли пароль с паролем в БД.

    Если есть ошибки, они возвращаются клиенту в виде json файла: сообщение об ошибке и время ошибки "message": "", "timestamp": ...

    Если ошибок в данных нет, то формируется jwt токен и в виде json согласно ТЗ отправляется клиенту
 */

@RestController
@RequestMapping("/auth")
public class PersonController {

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(JWTUtil jwtUtil,
                            ModelMapper modelMapper,
                            PersonService personService,
                            PersonValidator personValidator) {
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.personService = personService;
        this.personValidator = personValidator;
    }


    // сам метод пост-мэппинга
    @PostMapping("/login")
    public Map<String,String> authentication(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        //валидация данных
        personValidator.validate(convertToPerson(personDTO), bindingResult);

        // если есть ошибки формируем текст сообщения и выбрасываем MessageException
        if (bindingResult.hasErrors()) {
            CreateMessageException.returnErrorsToClient(bindingResult);
        }

        // генерируем и отправляем клиенту jwt токен в виде Json
        String token = jwtUtil.generateToken(personDTO.getName());
        return Collections.singletonMap("token", token);
    }

    // метод конвертации из DTO в модель
    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    // в этом методе ловим выброшенный MessageException и возвращаем клиенту текст ошибки и статус 400 BAD_REQUEST
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MessageException e) {
        // создаем наш объект ErrorResponse с ошибкой
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
}

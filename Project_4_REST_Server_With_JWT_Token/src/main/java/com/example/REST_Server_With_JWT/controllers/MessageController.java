package com.example.REST_Server_With_JWT.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.REST_Server_With_JWT.dto.MessageDTO;
import com.example.REST_Server_With_JWT.models.Message;
import com.example.REST_Server_With_JWT.security.JWTUtil;
import com.example.REST_Server_With_JWT.services.MessageService;
import com.example.REST_Server_With_JWT.util.exceptions.CreateMessageException;
import com.example.REST_Server_With_JWT.util.exceptions.MessageException;
import com.example.REST_Server_With_JWT.util.responses.ErrorResponse;
import com.example.REST_Server_With_JWT.util.responses.MessagesResponse;
import com.example.REST_Server_With_JWT.util.validators.MessageValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    В данном контроллере обрабатывается POST запрос по url /message, на вход подается json со структурой согласно ТЗ,
    первым делом получаем токен из хедера, проводим валидацию токена, если валидация токена не прошла, выбрасываем JWTVerificationException
    и отправляем клиенту описание ошибки в виде json файла: "message": "сообщение об ошибке", "timestamp": время ошибки

    После проверки токена проверяются данные на правильность, существует ли данный человек в БД, не пустое ли поле с именем name и message,
    если есть ошибки, они возвращаются клиенту в виде json файла: сообщение об ошибке и время ошибки "message": "", "timestamp": ...

    Если ошибок в данных нет, то происходит запись сообщения в БД. Обратно клиенту, с целью унификации кода возвращается json без сообщений.

    Если в сообщении указан текст "history 10", то клиенту возвращается 10 последних сообщений из БД в виде json файла, все сообщения
    оборачиваются в обертку MessagesResponse для удобства получения сообщений клиентом.
 */

@RestController
public class MessageController {

    private final int SEVEN_CHARS = 7;

    private final String HISTORY10 = "history 10";

    private final JWTUtil jwtUtil;
    private final MessageService messageService;
    private final ModelMapper modelMapper;
    private final MessageValidator messageValidator;

    @Autowired
    public MessageController(JWTUtil jwtUtil,
                             MessageService messageService,
                             ModelMapper modelMapper,
                             MessageValidator messageValidator) {
        this.jwtUtil = jwtUtil;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
        this.messageValidator = messageValidator;
    }

    @PostMapping("/message")
    public MessagesResponse getMessage(@RequestHeader(name = "Authorization") String header,
                                       @RequestBody @Valid MessageDTO messageDTO,
                                       BindingResult bindingResult) {

        // получаем токен начиная с 7 индекса, кроме слова "Bearer_"
        String token = header.substring(SEVEN_CHARS);
        // валидируем токен, если токен не валидируется, например, просрочен, то выбросим JWTVerificationException
        jwtUtil.validateTokenAndRetrieveClaim(token);

        //валидируем полученные от клиента данные
        messageValidator.validate(convertToMessage(messageDTO), bindingResult);

        // если есть ошибки формируем текст сообщения и выбрасываем MessageException
        if (bindingResult.hasErrors()) {
            CreateMessageException.returnErrorsToClient(bindingResult);
        }

        List<Message> messages = new ArrayList<>();

        // если в сообщении есть текст "history 10", то формирует список из 10 последних сообщений,
        // если обычное сообщение, то записываем его в БД
        if (messageDTO.getMessage().equals(HISTORY10)) {
            messages = messageService.get10LastMessages();
        } else {
            messages = messageService.save(convertToMessage(messageDTO));
        }

        // преобразуем список сообщений в DTO, оборачиваем в MessagesResponse и отправляем клиенту
        return new MessagesResponse(
                messages.
                stream().
                map(this::convertToMessageDTO).
                collect(Collectors.toList())
        );
    }

    // метод конвертации из DTO в модель
    private Message convertToMessage(MessageDTO messageDTO) {
        return modelMapper.map(messageDTO, Message.class);
    }

    // метод конвертации из модели в DTO
    private MessageDTO convertToMessageDTO(Message message) {
        return modelMapper.map(message, MessageDTO.class);
    }

    // в этом методе ловим выброшенный MessageException и возвращаем клиенту текст ошибки и статус 400 BAD_REQUEST
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MessageException e) {
        return new ResponseEntity<>(getErrorResponse(e), HttpStatus.BAD_REQUEST);
    }

    // в этом методе ловим выброшенный JWTVerificationException и возвращаем клиенту текст ошибки и статус 400 BAD_REQUEST
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(JWTVerificationException e) {
        return new ResponseEntity<>(getErrorResponse(e), HttpStatus.BAD_REQUEST);
    }

    // в этом методе создаем ErrorResponse с ошибкой и временем возникновения
    private ErrorResponse getErrorResponse(Exception e) {
        // создаем наш объект ErrorResponse с ошибкой
        return new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
    }

}

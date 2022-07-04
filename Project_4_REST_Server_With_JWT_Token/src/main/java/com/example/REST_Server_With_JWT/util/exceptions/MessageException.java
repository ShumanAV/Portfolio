package com.example.REST_Server_With_JWT.util.exceptions;

// создадим свое собственное исключение, данное Exception о том, что в тех данных, которые прислал клиент, были ошибки
public class MessageException extends RuntimeException{
    public MessageException(String msg) {
        // будем принимать сообщение об ошибке и передавать его в супер класс RuntimeException
        super(msg);
    }
}

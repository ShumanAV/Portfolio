package com.example.REST_Server_With_JWT.util.responses;

// Класс обертка для ошибок,
// объекты этого класса будут отправляться в виде json если будут ошибки
public class ErrorResponse {

    // поле с сообщением об ошибке
    private String message;

    // поле с временем ошибки
    private long timestamp;

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

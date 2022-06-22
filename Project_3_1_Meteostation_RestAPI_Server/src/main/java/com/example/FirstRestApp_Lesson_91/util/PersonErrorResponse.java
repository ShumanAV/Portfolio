package com.example.FirstRestApp_Lesson_91.util;

// объекты этого класса будут отправляться если у нас будет ошибка, например, id несуществующего элемента
public class PersonErrorResponse {

    // поле с сообщением об ошибке
    private String message;

    // поле с временем ошибки
    private long timestamp;

    public PersonErrorResponse(String message, long timestamp) {
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

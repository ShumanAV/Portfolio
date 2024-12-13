package ru.shuman.Project_Aibolit_Server.util.errors;

//Класс обертка для отправки клиенту сообщения об ошибке в процессе запроса одного доктора по id, сохранения нового
// или апдейте существующего доктора
public class DoctorErrorResponse {
    private String message;
    private long timestamp;

    public DoctorErrorResponse(String message, long timestamp) {
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

package ru.shuman.Project_Aibolit_Server.util.errors;

//Класс обертка для отправки клиенту сообщения об ошибке в процессе запроса одного договора по id, сохранения нового
// или апдейте существующего договора
public class ContractErrorResponse {
    private String message;
    private long timestamp;

    public ContractErrorResponse(String message, long timestamp) {
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

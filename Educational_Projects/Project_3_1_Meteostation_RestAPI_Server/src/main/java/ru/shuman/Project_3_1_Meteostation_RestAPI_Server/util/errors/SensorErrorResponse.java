package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.util.errors;

/*
Класс обертка для отправки клиенту ошибок, которые возникают при валидации новых сенсоров,
в обертке есть поле для сообщений об ошибке и время
 */

public class SensorErrorResponse {
    private String message;
    private long timestemp;

    public SensorErrorResponse(String message, long timestemp) {
        this.message = message;
        this.timestemp = timestemp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(long timestemp) {
        this.timestemp = timestemp;
    }
}

package ru.shuman.Project_Aibolit_Server.util.errors;

public class SpecializationErrorResponse {
    private String message;
    private long timestamp;

    public SpecializationErrorResponse(String message, long timestamp) {
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

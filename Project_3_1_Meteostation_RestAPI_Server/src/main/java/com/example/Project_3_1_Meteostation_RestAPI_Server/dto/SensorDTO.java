package com.example.Project_3_1_Meteostation_RestAPI_Server.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// этот класс уже будет содержать те поля, которые мы будем отправлять клиенту или получать от клиента
// DTO никак не связан с таблицей в БД
public class SensorDTO {

    @NotNull
    @NotEmpty(message = "Name of sensor should not be empty")
    @Size(min = 3, max = 30, message = "Name of sensor should be between 3 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

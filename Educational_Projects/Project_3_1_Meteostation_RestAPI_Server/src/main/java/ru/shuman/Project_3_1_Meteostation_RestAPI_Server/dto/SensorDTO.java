package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*
DTO сенсора
 */
public class SensorDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    public SensorDTO() {
    }

    public SensorDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

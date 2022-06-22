package com.example.Project_3_1_Meteostation_RestAPI_Server.dto;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class MeasurementDTO {

    @Min(value = -100, message = "Min value should be greater than -100C")
    @Max(value = 100, message = "Min value should be greater than 100C")
    @Column(name = "temperature_value")
    private float value;

    @NotEmpty(message = "Raining should not be empty")
    @Column(name = "raining")
    private boolean raining;

    @NotEmpty(message = "Name of Sensor should not be empty")
    private String[] sensor;

    public float getValue() {
        return value;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }
}

package com.example.Project_3_1_Meteostation_RestAPI_Server.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {

    @NotNull
    @Min(value = -100, message = "Min value should be greater or equal to -100")
    @Max(value = 100, message = "Max value should be less or equal to 100")
    private Float value;

    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;

    public float getValue() {
        return value;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
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

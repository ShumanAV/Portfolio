package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/*
DTO измерения
 */
public class MeasurementDTO {
    @NotNull(message = "Temperature should be not be null")
    @Min(value = -100, message = "Temperature should be greater than -100")
    @Max(value = 100, message = "Temperature should be below than 100")
    private Float value;
    @NotNull(message = "Raining should be not be null")
    private Boolean raining;
    @NotNull(message = "Sensor should be not be null")
    private SensorDTO sensor;
    private LocalDateTime createdAt;

    public MeasurementDTO() {
    }

    public MeasurementDTO(float value, boolean raining, LocalDateTime createdAt) {
        this.value = value;
        this.raining = raining;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "temperature=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                ", createdAt=" + createdAt +
                '}';
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}

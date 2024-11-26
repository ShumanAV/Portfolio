package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/*
Модель измерения
 */

@Entity
@Table(name = "measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Sensor should be not be null")
    @Min(value = -100, message = "Temperature should be greater than -100")
    @Max(value = 100, message = "Temperature should be below than 100")
    @Column(name = "temperature")
    private Float value;
    @NotNull(message = "Sensor should be not be null")
    @Column(name = "raining")
    private Boolean raining;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @NotNull(message = "Sensor should be not be null")
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(float value, boolean raining, LocalDateTime createdAt, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.createdAt = createdAt;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "temperature=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Boolean getRaining() {
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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}

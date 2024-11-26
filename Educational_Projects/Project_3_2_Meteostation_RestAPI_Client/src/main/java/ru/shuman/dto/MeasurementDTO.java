package ru.shuman.dto;

/*
DTO Измерения
 */
public class MeasurementDTO {
    private double value;
    private boolean raining;
    private SensorDTO sensor;
    private String createdAt;

    public MeasurementDTO() {
    }

    public MeasurementDTO(double value, boolean raining, SensorDTO sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "temperature=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                ", created at=" + createdAt +
                '}';
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}

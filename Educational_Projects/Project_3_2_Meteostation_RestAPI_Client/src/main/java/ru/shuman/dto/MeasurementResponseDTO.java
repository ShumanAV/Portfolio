package ru.shuman.dto;

import java.util.List;

/*
DTO обертка для получения списка всех измерений с сервера
 */

public class MeasurementResponseDTO {
    private List<MeasurementDTO> measurements;

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}

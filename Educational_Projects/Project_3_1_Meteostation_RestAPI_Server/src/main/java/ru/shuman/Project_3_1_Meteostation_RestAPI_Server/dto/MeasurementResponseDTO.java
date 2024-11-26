package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.dto;

import java.util.List;

public class MeasurementResponseDTO {
    private List<MeasurementDTO> measurements;

    public MeasurementResponseDTO(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}

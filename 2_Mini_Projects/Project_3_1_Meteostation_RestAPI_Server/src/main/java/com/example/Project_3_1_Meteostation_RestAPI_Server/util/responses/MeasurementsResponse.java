package com.example.Project_3_1_Meteostation_RestAPI_Server.util.responses;

import com.example.Project_3_1_Meteostation_RestAPI_Server.dto.MeasurementDTO;

import java.util.List;

// класс обертка для отправки списка измерений в виде Json
public class MeasurementsResponse {

    private List<MeasurementDTO> measurements;

    public MeasurementsResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}

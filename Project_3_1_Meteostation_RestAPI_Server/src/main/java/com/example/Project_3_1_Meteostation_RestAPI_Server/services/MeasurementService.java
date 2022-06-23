package com.example.Project_3_1_Meteostation_RestAPI_Server.services;

import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import com.example.Project_3_1_Meteostation_RestAPI_Server.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final SensorService sensorService;
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(SensorService sensorService, MeasurementRepository measurementRepository) {
        this.sensorService = sensorService;
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> findAllMeasurements() {
        return measurementRepository.findAll();
    }

    public int returnRainyDaysCount() {
        return measurementRepository.findAllByRainingTrue().size();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setCreatedAt(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

}

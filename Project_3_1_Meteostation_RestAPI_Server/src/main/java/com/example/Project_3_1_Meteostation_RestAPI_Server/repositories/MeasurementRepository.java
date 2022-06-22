package com.example.Project_3_1_Meteostation_RestAPI_Server.repositories;

import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAllByRainingTrue();
}

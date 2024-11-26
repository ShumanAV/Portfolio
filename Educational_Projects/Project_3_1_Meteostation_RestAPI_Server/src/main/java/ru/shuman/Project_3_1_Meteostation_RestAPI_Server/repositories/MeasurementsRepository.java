package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;

import java.util.List;

/*
Репозиторий измерений
 */

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findBySensor(Sensor sensor);
    List<Measurement> findByRainingTrue();
}

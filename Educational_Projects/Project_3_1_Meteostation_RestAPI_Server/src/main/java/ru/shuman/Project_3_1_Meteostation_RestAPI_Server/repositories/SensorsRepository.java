package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;

import java.util.Optional;

/*
Репозиторий сенсоров
 */

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}

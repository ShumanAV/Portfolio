package com.example.Project_3_1_Meteostation_RestAPI_Server.repositories;

import com.example.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// JpaRepository параметризуем классом, который является сущностью и тип первичного ключа
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}

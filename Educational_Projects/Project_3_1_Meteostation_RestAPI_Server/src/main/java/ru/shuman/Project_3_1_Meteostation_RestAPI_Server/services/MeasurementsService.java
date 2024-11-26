package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Measurement;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    /*
    Метод формирует список всех измерений из БД и возвращает его
     */
    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    /*
    Метод формирует количество дождливых дней, которые были за все время и возвращает его
     */
    public Long getRainyDays(){
        return (long) measurementsRepository.findByRainingTrue().size();
    }

    /*
    Метод принимает новое измерение, добавляет в измерение текущую дату и время, т.к. в измерении сенсор указан, но есть
    только название, находит сенсор по имени в БД и устанавливает его в измерение, далее сохраняет новое измерение в БД,
    также для кэша в сенсор добавляет данное измерение
     */
    @Transactional
    public void register(Measurement measurement){
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
        measurementsRepository.save(measurement);

        //для кэша
        measurement.getSensor().getMeasurements().add(measurement);
    }
}

package ru.shuman.Project_3_1_Meteostation_RestAPI_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.models.Sensor;
import ru.shuman.Project_3_1_Meteostation_RestAPI_Server.repositories.SensorsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    /*
    Метод формирует и возвращает все сенсоры из БД
     */
    public List<Sensor> findAll(){
        return sensorsRepository.findAll();
    }

    /*
    Метод принимает название сенсора, ищет по названию сенсор в БД и возвращает его при наличии в обертке Optional
     */
    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    /*
    Метод принимает на вход новый сенсор и сохраняет его в БД
     */
    @Transactional
    public void register(Sensor sensor){
        sensorsRepository.save(sensor);
    }
}

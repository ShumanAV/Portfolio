package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.PlaceStudy;
import ru.shuman.Project_Aibolit_Server.repositories.PlaceStudyRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlaceStudyService {

    private final PlaceStudyRepository placeStudyRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PlaceStudyService(PlaceStudyRepository placeStudyRepository) {
        this.placeStudyRepository = placeStudyRepository;
    }

    /*
    Метод формирует и возвращает список всех мест учебы пациентов
     */
    public List<PlaceStudy> findAll() {
        return placeStudyRepository.findAll();
    }

    /*
    Метод ищет место учебы пациента по id и возвращает его
     */
    public Optional<PlaceStudy> findById(Integer placeStudyId) {
        return placeStudyRepository.findById(placeStudyId);
    }

    /*
    Метод сохраняет новое место учебы пациентов в БД
     */
    @Transactional
    public void create(PlaceStudy newPlaceStudy) {
        placeStudyRepository.save(newPlaceStudy);
    }

    /*
    Метод сохраняет измененное место учебы пациентов в БД
     */
    @Transactional
    public void update(PlaceStudy updatedPlaceStudy) {
        placeStudyRepository.save(updatedPlaceStudy);
    }
}

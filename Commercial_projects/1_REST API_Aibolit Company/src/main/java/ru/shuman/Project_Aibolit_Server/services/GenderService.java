package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.GenderRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;

@Service
@Transactional(readOnly = true)
public class GenderService {

    private final GenderRepository genderRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    /*
    Метод формирует и возвращает список всех гендеров
     */
    public List<Gender> findAll() {
        return genderRepository.findAll();
    }

    /*
    Метод ищет гендер по названию и возвращает его в обертке Optional
     */
    public Optional<Gender> findByName(String name) {
        return genderRepository.findByName(name);
    }

    /*
    Метод ищет гендер по id и возвращет его в обертке Optional
     */
    public Optional<Gender> findById(Integer genderId) {
        return genderRepository.findById(genderId);
    }

    /*
    Метод сохраняет новый гендер в БД
     */
    @Transactional
    public void create(Gender newGender) {
        genderRepository.save(newGender);
    }

    /*
    Метод сохраняет измененный гендер в БД
     */
    @Transactional
    public void update(Gender updatedGender) {
        genderRepository.save(updatedGender);
    }

    /*
    Метод добавляет пациента в список пациентов для гендера указанного в пациенте, делается это для кэша
     */
    public void addPatientAtListForGender(Patient patient, Gender gender) {
        addObjectOneInListForObjectTwo(patient, gender, this);
    }

    /*
    Метод добавляет родителя пациента в список родителей для гендера указанного в родителе, делается это для кэша
     */
    public void addParentAtListForGender(Parent parent, Gender gender) {
        addObjectOneInListForObjectTwo(parent, gender, this);
    }
}

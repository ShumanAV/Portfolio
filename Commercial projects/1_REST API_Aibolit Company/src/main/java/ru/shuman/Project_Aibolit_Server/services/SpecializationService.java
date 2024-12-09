package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Specialization;
import ru.shuman.Project_Aibolit_Server.models.Doctor;
import ru.shuman.Project_Aibolit_Server.repositories.SpecializationRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;
import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

@Service
@Transactional(readOnly = true)
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    /*
    Метод ищет специализацию по имени и возвращает ее в обертке Optional
     */
    public Optional<Specialization> findByName(String name) {
        return specializationRepository.findByName(name);
    }

    /*
    Метод ищет специализацию по id и возвращает ее в обертке Optional
     */
    public Optional<Specialization> findById(Integer specializationId) {
        return specializationRepository.findById(specializationId);
    }

    /*
    Метод формирует и возвращает список всех специализаций
    */
    public List<Specialization> findAll() {
        return specializationRepository.findAll();
    }

    /*
    Метод формирует и возвращает список всех специализаций с учетом флага published
     */
    public List<Specialization> findAllByPublished(Boolean published) {
        return specializationRepository.findByPublished(published);
    }

    /*
    Метод сохраняет новую специализацию в БД, заполняет дату и время создания и изменения
     */
    @Transactional
    public void create(Specialization newSpecialization) {

        //записываем дату и время создания и изменения специализации
        newSpecialization.setCreatedAt(LocalDateTime.now());
        newSpecialization.setUpdatedAt(LocalDateTime.now());

        //сохраняем новую специализацию в БД
        specializationRepository.save(newSpecialization);
    }

    /*
    Метод сохраняет измененную специализацию, копирует значения всех не null полей из изменяемой специализации
     в существующую в БД, id и время создания остаются без изменений
     */
    @Transactional
    public void update(Specialization updatedSpecialization) {

        //находим существующую специализацию в БД по id
        Specialization existingSpecialization = specializationRepository.findById(updatedSpecialization.getId()).get();

        //копируем значения всех полей кроме тех, которые null, из измененного типа договора в существующий
        copyNonNullProperties(updatedSpecialization, existingSpecialization);

        //обновляем дату и время изменения специализации
        existingSpecialization.setUpdatedAt(LocalDateTime.now());
    }

    /*
    Метод добавляет доктора в список докторов для специализации, делается это как для кэша
    */
    public void addDoctorAtListForSpecialization(Doctor doctor, Specialization specialization) {
        addObjectOneInListForObjectTwo(doctor, specialization, this);
    }
}

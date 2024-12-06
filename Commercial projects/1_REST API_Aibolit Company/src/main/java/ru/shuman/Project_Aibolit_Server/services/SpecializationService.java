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
    Метод сохраняет новую специализацию, специализация приходит, заполняются поля дата и время создания и изменения
     */
    @Transactional
    public void create(Specialization newSpecialization) {

        newSpecialization.setCreatedAt(LocalDateTime.now());
        newSpecialization.setUpdatedAt(LocalDateTime.now());

        specializationRepository.save(newSpecialization);
    }

    /*
    Метод сохраняет изменения, которые были сделаны в изменяемой специализации, специализация приходит,
    копируем значения всех не null полей из изменяемой специализации в существующую в БД, id и время создания остаются
    без изменений
     */
    @Transactional
    public void update(Specialization updatedSpecialization) {

        Specialization existingSpecialization = specializationRepository.findById(updatedSpecialization.getId()).get();

        copyNonNullProperties(updatedSpecialization, existingSpecialization);

        existingSpecialization.setUpdatedAt(LocalDateTime.now());
    }

    /*
    Метод добавляет доктора в список специализации, делается это как для кэша
    */

    public void addDoctorAtListForSpecialization(Doctor doctor, Specialization specialization) {
        GeneralMethods.addObjectOneInListForObjectTwo(doctor, specialization, this);
    }
}

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
    public void create(Specialization specialization) {

        specialization.setCreatedAt(LocalDateTime.now());
        specialization.setUpdatedAt(LocalDateTime.now());

        specializationRepository.save(specialization);
    }

    /*
    Метод сохраняет измененную специализацию, специализация приходит, чтобы сохранить время создания, сначала находим по
    id существующую специализацию и в измененную сохраняем время создания
     */
    @Transactional
    public void update(Specialization specialization) {

        Specialization existingSpecialization = specializationRepository.findById(specialization.getId()).get();

        specialization.setCreatedAt(existingSpecialization.getCreatedAt());
        specialization.setUpdatedAt(LocalDateTime.now());

        specializationRepository.save(specialization);
    }

    /*
    Метод добавляет доктора в список специализации, делается это как для кэша
    */

    public void addDoctorAtListForSpecialization(Doctor doctor, Specialization specialization) {
        GeneralMethods.addObjectOneInListForObjectTwo(doctor, specialization, this);
    }
}

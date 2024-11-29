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

    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Optional<Specialization> findByName(String name) {
        return specializationRepository.findByName(name);
    }

    public Optional<Specialization> findById(Integer specializationId) {
        return specializationRepository.findById(specializationId);
    }

    public List<Specialization> findAll() {
        return specializationRepository.findAll();
    }

    public List<Specialization> findAllByPublished(Boolean published) {
        return specializationRepository.findByPublished(published);
    }

    @Transactional
    public void create(Specialization specialization) {

        specialization.setCreatedAt(LocalDateTime.now());
        specialization.setUpdatedAt(LocalDateTime.now());

        specializationRepository.save(specialization);
    }

    @Transactional
    public void update(Specialization specialization) {

        specialization.setUpdatedAt(LocalDateTime.now());

        specializationRepository.save(specialization);
    }

    /*
    Метод осуществляет поиск в БД выбранной специализации со списком докторов,
    при наличии, и добавление в этот список данного доктора, если список докторов равен null, то создается
    new ArrayList<>().
    Делается это как для кэша, так и с целью выявления наличия списка докторов у данной специализации и добавление нового.
    */

    public void addDoctorAtListForSpecialization(Doctor doctor, Specialization specialization) {
        GeneralMethods.addObjectOneInListForObjectTwo(doctor, specialization, this);
    }
}

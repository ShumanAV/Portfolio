package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.GenderRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GenderService {

    private final GenderRepository genderRepository;

    @Autowired
    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public Optional<Gender> findById(Integer genderId) {
        return genderRepository.findById(genderId);
    }

    public void setPatientsForGender(Patient patient, Gender gender) {
        GeneralMethods.addObjectOneInListForObjectTwo(patient, gender, this);
    }

    public void setParentsForGender(Parent parent, Gender gender) {
        GeneralMethods.addObjectOneInListForObjectTwo(parent, gender, this);
    }

    @Transactional
    public void create(Gender gender) {
        genderRepository.save(gender);
    }

    @Transactional
    public void update(Gender gender) {
        genderRepository.save(gender);
    }

    public List<Gender> findAll() {
        return genderRepository.findAll();
    }
}

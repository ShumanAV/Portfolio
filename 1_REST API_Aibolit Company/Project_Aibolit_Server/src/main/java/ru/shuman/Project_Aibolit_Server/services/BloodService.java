package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.repositories.BloodRepository;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BloodService {

    private final BloodRepository bloodRepository;

    @Autowired
    public BloodService(BloodRepository bloodRepository) {
        this.bloodRepository = bloodRepository;
    }

    public Optional<Blood> findById(Integer bloodId) {
        return bloodRepository.findById(bloodId);
    }

    public void setPatientsForBlood(Patient patient, Blood blood) {
        StandardMethods.addObjectOneInListForObjectTwo(patient, blood, this);
    }

    public void setParentsForBlood(Parent parent, Blood blood) {
        StandardMethods.addObjectOneInListForObjectTwo(parent, blood, this);
    }

    @Transactional
    public void create(Blood blood) {
        bloodRepository.save(blood);
    }

    @Transactional
    public void update(Blood blood) {
        bloodRepository.save(blood);
    }

    public List<Blood> findAll() {
        return bloodRepository.findAll();
    }
}

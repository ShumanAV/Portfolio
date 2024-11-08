package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Education;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.repositories.EducationRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Optional<Education> findById(Integer educationId) {
        return educationRepository.findById(educationId);
    }

    public void setParentsForEducation(Parent parent, Education education) {
        GeneralMethods.addObjectOneInListForObjectTwo(parent, education, this);
    }

    @Transactional
    public void create(Education education) {
        educationRepository.save(education);
    }

    @Transactional
    public void update(Education education) {
        educationRepository.save(education);
    }

    public List<Education> findAll() {
        return educationRepository.findAll();
    }
}

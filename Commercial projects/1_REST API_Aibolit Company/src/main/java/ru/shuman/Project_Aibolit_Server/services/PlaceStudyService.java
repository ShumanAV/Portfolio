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

    @Autowired
    public PlaceStudyService(PlaceStudyRepository placeStudyRepository) {
        this.placeStudyRepository = placeStudyRepository;
    }

    public Optional<PlaceStudy> findById(Integer placeStudyId) {
        return placeStudyRepository.findById(placeStudyId);
    }

    @Transactional
    public void create(PlaceStudy placeStudy) {
        placeStudyRepository.save(placeStudy);
    }

    @Transactional
    public void update(PlaceStudy placeStudy) {
        placeStudyRepository.save(placeStudy);
    }

    public List<PlaceStudy> findAll() {
        return placeStudyRepository.findAll();
    }
}

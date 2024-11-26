package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Diary;
import ru.shuman.Project_Aibolit_Server.models.PlaceStudy;

import java.util.Optional;

@Repository
public interface PlaceStudyRepository extends JpaRepository<PlaceStudy, Integer> {
    Optional<PlaceStudy> findByName(String name);
}

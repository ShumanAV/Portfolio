package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.models.Diary;

@Repository
public interface BloodRepository extends JpaRepository<Blood, Integer> {
}

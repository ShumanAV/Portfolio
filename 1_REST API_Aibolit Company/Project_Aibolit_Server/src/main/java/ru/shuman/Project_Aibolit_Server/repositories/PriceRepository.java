package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Diary;
import ru.shuman.Project_Aibolit_Server.models.Price;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
    List<Price> findByPublished(Boolean published);
}

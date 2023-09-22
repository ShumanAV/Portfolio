package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.models.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
}

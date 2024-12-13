package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.TypeContract;

@Repository
public interface TypeContractRepository extends JpaRepository<TypeContract, Integer> {
}

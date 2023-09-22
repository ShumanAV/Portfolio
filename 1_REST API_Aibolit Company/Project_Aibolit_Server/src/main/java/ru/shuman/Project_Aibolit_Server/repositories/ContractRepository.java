package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.models.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
}

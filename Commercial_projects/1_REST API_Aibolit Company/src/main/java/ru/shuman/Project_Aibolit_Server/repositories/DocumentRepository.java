package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Document;
import ru.shuman.Project_Aibolit_Server.models.Parent;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
}

package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;

import java.util.Optional;

@Repository
public interface TypeDocRepository extends JpaRepository<TypeDoc, String> {
    Optional<TypeDoc> findByName(String name);
    Optional<TypeDoc> findById(Integer id);
}

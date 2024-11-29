package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.TypeEmployment;
import ru.shuman.Project_Aibolit_Server.repositories.TypeEmploymentRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TypeEmploymentService {

    private final TypeEmploymentRepository typeEmploymentRepository;

    @Autowired
    public TypeEmploymentService(TypeEmploymentRepository typeEmploymentRepository) {
        this.typeEmploymentRepository = typeEmploymentRepository;
    }

    public Optional<TypeEmployment> findById(Integer idTypeEmployment) {
        return typeEmploymentRepository.findById(idTypeEmployment);
    }

    public void addParentAtListForTypeEmployment(Parent parent, TypeEmployment typeEmployment) {
        GeneralMethods.addObjectOneInListForObjectTwo(parent, typeEmployment, this);
    }

    @Transactional
    public void create(TypeEmployment typeEmployment) {
        typeEmploymentRepository.save(typeEmployment);
    }

    @Transactional
    public void update(TypeEmployment typeEmployment) {
        typeEmploymentRepository.save(typeEmployment);
    }

    public List<TypeEmployment> findAll() {
        return typeEmploymentRepository.findAll();
    }

    @Transactional
    public void delete(TypeEmployment typeEmployment) {
        typeEmploymentRepository.delete(typeEmployment);
    }
}

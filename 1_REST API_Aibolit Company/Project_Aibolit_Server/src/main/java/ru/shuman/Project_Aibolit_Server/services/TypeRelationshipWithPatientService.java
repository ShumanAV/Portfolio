package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.TypeRelationshipWithPatient;
import ru.shuman.Project_Aibolit_Server.repositories.TypeRelationshipWithPatientRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TypeRelationshipWithPatientService {

    private final TypeRelationshipWithPatientRepository typeRelationshipWithPatientRepository;

    @Autowired
    public TypeRelationshipWithPatientService(TypeRelationshipWithPatientRepository typeRelationshipWithPatientRepository) {
        this.typeRelationshipWithPatientRepository = typeRelationshipWithPatientRepository;
    }

    public Optional<TypeRelationshipWithPatient> findById(Integer id) {
        return typeRelationshipWithPatientRepository.findById(id);
    }

    public void setParentsForTypeRelationship(Parent parent, TypeRelationshipWithPatient typeRelationship) {
        GeneralMethods.addObjectOneInListForObjectTwo(parent, typeRelationship, this);
    }

    @Transactional
    public void create(TypeRelationshipWithPatient typeRelationship) {
        typeRelationshipWithPatientRepository.save(typeRelationship);
    }

    @Transactional
    public void update(TypeRelationshipWithPatient typeRelationship) {
        typeRelationshipWithPatientRepository.save(typeRelationship);
    }

    public List<TypeRelationshipWithPatient> findAll() {
        return typeRelationshipWithPatientRepository.findAll();
    }
}

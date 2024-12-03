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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeRelationshipWithPatientService(TypeRelationshipWithPatientRepository typeRelationshipWithPatientRepository) {
        this.typeRelationshipWithPatientRepository = typeRelationshipWithPatientRepository;
    }

    /*
    Метод формирует список всех типов отношений родителей с пациентами и возвращает его
    */
    public List<TypeRelationshipWithPatient> findAll() {
        return typeRelationshipWithPatientRepository.findAll();
    }

    /*
    Метод находит тип отношения родителей с пациентом по названию и возвращает его в обертке Optional
     */
    public Optional<TypeRelationshipWithPatient> findByName(String name) {
        return typeRelationshipWithPatientRepository.findByName(name);
    }

    /*
    Метод находит тип отношения родителей с пациентом по id и возвращает его в обертке Optional
     */
    public Optional<TypeRelationshipWithPatient> findById(Integer id) {
        return typeRelationshipWithPatientRepository.findById(id);
    }

    /*
    Метод сохраняет новый тип отношений родителей с пациентом в БД
     */
    @Transactional
    public void create(TypeRelationshipWithPatient typeRelationship) {
        typeRelationshipWithPatientRepository.save(typeRelationship);
    }

    /*
    Метод изменяет существующий тип отношений родителей с пациентом в БД
     */
    @Transactional
    public void update(TypeRelationshipWithPatient typeRelationship) {
        typeRelationshipWithPatientRepository.save(typeRelationship);
    }

    /*
    Метод добавляет родителя в лист типа отношений родителей с пациентом, делается это для кэша
    */
    public void addParentAtListForTypeRelationship(Parent parent, TypeRelationshipWithPatient typeRelationship) {
        GeneralMethods.addObjectOneInListForObjectTwo(parent, typeRelationship, this);
    }
}

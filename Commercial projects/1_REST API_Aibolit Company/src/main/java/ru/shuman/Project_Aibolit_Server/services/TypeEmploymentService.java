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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeEmploymentService(TypeEmploymentRepository typeEmploymentRepository) {
        this.typeEmploymentRepository = typeEmploymentRepository;
    }

    /*
    Метод формирует список всех типов занятости и возвращает его
    */
    public List<TypeEmployment> findAll() {
        return typeEmploymentRepository.findAll();
    }

    /*
    Метод находит тип занятости по названию и возвращает его в обертке Optional
     */
    public Optional<TypeEmployment> findByName(String name) {
        return typeEmploymentRepository.findByName(name);
    }

    /*
    Метод находит тип занятости по id и возвращает его в обертке Optional
     */
    public Optional<TypeEmployment> findById(Integer idTypeEmployment) {
        return typeEmploymentRepository.findById(idTypeEmployment);
    }

    /*
    Метод сохраняет новый тип занятости в БД
     */
    @Transactional
    public void create(TypeEmployment typeEmployment) {
        typeEmploymentRepository.save(typeEmployment);
    }

    /*
    Метод изменяет существующий тип занятости в БД
     */
    @Transactional
    public void update(TypeEmployment typeEmployment) {
        typeEmploymentRepository.save(typeEmployment);
    }

    /*
    Метод добавляет родителя в лист типа занятости, делается это для кэша
    */
    public void addParentAtListForTypeEmployment(Parent parent, TypeEmployment typeEmployment) {
        GeneralMethods.addObjectOneInListForObjectTwo(parent, typeEmployment, this);
    }
}

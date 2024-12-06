package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.TypeDocRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TypeDocService {

    private final TypeDocRepository typeDocRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeDocService(TypeDocRepository typeDocRepository) {
        this.typeDocRepository = typeDocRepository;
    }

    /*
    Метод формирует список всех типов документов и возвращает его
    */
    public List<TypeDoc> findAll() {
        return typeDocRepository.findAll();
    }

    /*
    Метод находит тип документа по id и возвращает его в обертке Optional
     */
    public Optional<TypeDoc> findById(Integer typeDocId) {
        return typeDocRepository.findById(typeDocId);
    }

    /*
    Метод находит тип документа по названию и возвращает его в обертке Optional
     */
    public Optional<TypeDoc> findByName(String name) {
        return typeDocRepository.findByName(name);
    }

    /*
    Метод сохраняет новый тип документа в БД
     */
    @Transactional
    public void create(TypeDoc newTypeDoc) {
        typeDocRepository.save(newTypeDoc);
    }

    /*
    Метод изменяет существующий тип документа в БД
     */
    @Transactional
    public void update(TypeDoc updatedTypeDoc) {
        typeDocRepository.save(updatedTypeDoc);
    }

    /*
    Метод добавляет документ в лист типа документа, делается это для кэша
    */
    public void addDocumentAtListForTypeDoc(Document document, TypeDoc typeDoc) {
        GeneralMethods.addObjectOneInListForObjectTwo(document, typeDoc, this);
    }
}

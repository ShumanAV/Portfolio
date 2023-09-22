package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.TypeDocRepository;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TypeDocService {

    private final TypeDocRepository typeDocRepository;

    @Autowired
    public TypeDocService(TypeDocRepository typeDocRepository) {
        this.typeDocRepository = typeDocRepository;
    }

    public Optional<TypeDoc> findById(String typeDocId) {
        return typeDocRepository.findById(typeDocId);
    }

    public Optional<TypeDoc> findById(Integer typeDocId) {
        return typeDocRepository.findById(typeDocId);
    }

    public Optional<TypeDoc> findByName(String name) {
        return typeDocRepository.findByName(name);
    }

    public void setDocumentsForTypeDoc(Document document, TypeDoc typeDoc) {
        StandardMethods.addObjectOneInListForObjectTwo(document, typeDoc, this);
    }

    @Transactional
    public void create(TypeDoc typeDoc) {
        typeDocRepository.save(typeDoc);
    }

    @Transactional
    public void update(TypeDoc typeDoc) {
        typeDocRepository.save(typeDoc);
    }

    public List<TypeDoc> findAll() {
        return typeDocRepository.findAll();
    }
}

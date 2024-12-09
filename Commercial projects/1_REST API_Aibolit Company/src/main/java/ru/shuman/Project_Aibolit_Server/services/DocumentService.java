package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Document;
import ru.shuman.Project_Aibolit_Server.repositories.DocumentRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TypeDocService typeDocService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public DocumentService(DocumentRepository documentRepository, TypeDocService typeDocService) {
        this.documentRepository = documentRepository;
        this.typeDocService = typeDocService;
    }

    /*
    Метод ищет документ по id и возвращает его в обертке Optional
     */
    public Optional<Document> findById(Integer documentId) {
        return documentRepository.findById(documentId);
    }

    /*
    Метод сохраняет новый документ в БД
     */
    @Transactional
    public void create(Document newDocument) {

        //для кэша добавляем документ в список документов у типа документа
        typeDocService.addDocumentAtListForTypeDoc(newDocument, newDocument.getTypeDoc());

        //сохраняем новый документ
        documentRepository.save(newDocument);
    }

    /*
    Метод сохраняет измененный документ в БД
     */
    @Transactional
    public void update(Document updatedDocument) {

        //для кэша добавляем документ в список документов для типа документа
        typeDocService.addDocumentAtListForTypeDoc(updatedDocument, updatedDocument.getTypeDoc());

        //сохраняем измененный документ
        documentRepository.save(updatedDocument);
    }
}

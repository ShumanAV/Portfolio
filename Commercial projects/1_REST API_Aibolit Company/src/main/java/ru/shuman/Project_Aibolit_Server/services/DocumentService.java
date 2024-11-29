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

    @Autowired
    public DocumentService(DocumentRepository documentRepository, TypeDocService typeDocService) {
        this.documentRepository = documentRepository;
        this.typeDocService = typeDocService;
    }

    public Optional<Document> findById(Integer documentId) {
        return documentRepository.findById(documentId);
    }

    @Transactional
    public void create(Document document) {

        typeDocService.addDocumentAtListForTypeDoc(document, document.getTypeDoc());

        documentRepository.save(document);
    }

    @Transactional
    public void update(Document document) {

        typeDocService.addDocumentAtListForTypeDoc(document, document.getTypeDoc());

        documentRepository.save(document);
    }
}

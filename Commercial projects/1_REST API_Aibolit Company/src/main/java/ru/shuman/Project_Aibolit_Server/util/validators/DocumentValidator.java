package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Document;
import ru.shuman.Project_Aibolit_Server.services.DocumentService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class DocumentValidator implements Validator {

    private final DocumentService documentService;
    private final TypeDocValidator typeDocValidator;
    private final TypeDocIdValidator typeDocIdValidator;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public DocumentValidator(DocumentService documentService, TypeDocValidator typeDocValidator, TypeDocIdValidator typeDocIdValidator) {
        this.documentService = documentService;
        this.typeDocValidator = typeDocValidator;
        this.typeDocIdValidator = typeDocIdValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Document.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Document document = (Document) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, document.getClass());

        //проверяем если есть id у типа документа, валидируем его id
        if (document.getTypeDoc().getId() != null) {
            typeDocIdValidator.validate(document.getTypeDoc(), errors);
        }
    }
}

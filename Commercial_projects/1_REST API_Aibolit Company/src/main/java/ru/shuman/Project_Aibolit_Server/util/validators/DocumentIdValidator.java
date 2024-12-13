package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Document;
import ru.shuman.Project_Aibolit_Server.services.DocumentService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class DocumentIdValidator implements Validator {

    private final DocumentService documentService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public DocumentIdValidator(DocumentService documentService) {
        this.documentService = documentService;
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

        //проверяем есть ли id у документа
        if (document.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У документа отсутствует id!");

            //проверяем есть ли документ в БД с таким id
        } else if (documentService.findById(document.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Документа с таким id не существует!");
        }
    }
}

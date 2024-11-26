package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Document;
import ru.shuman.Project_Aibolit_Server.services.DocumentService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class DocumentIdValidator implements Validator {

    private final DocumentService documentService;

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

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, document.getClass());

        if (document.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У документа отсутствует id!");

        } else if (documentService.findById(document.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Документа с таким id не существует!");
        }
    }
}

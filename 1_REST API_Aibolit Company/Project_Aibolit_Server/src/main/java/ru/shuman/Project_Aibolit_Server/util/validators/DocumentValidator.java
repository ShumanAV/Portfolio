package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Document;
import ru.shuman.Project_Aibolit_Server.services.DocumentService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class DocumentValidator implements Validator {

    private final DocumentService documentService;
    private final TypeDocValidator typeDocValidator;
    private final TypeDocIdValidator typeDocIdValidator;

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

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, document.getClass());

        if (document.getTypeDoc() == null) {
            errors.rejectValue(field == null ? "typeDoc": field, "", "У документа не выбран тип документа!");
        } else {
            typeDocIdValidator.validate(document.getTypeDoc(), errors);
            typeDocValidator.validate(document.getTypeDoc(), errors);
        }
    }
}

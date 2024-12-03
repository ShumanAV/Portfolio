package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;
import ru.shuman.Project_Aibolit_Server.services.TypeDocService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class TypeDocIdValidator implements Validator {

    private final TypeDocService typeDocService;

    @Autowired
    public TypeDocIdValidator(TypeDocService typeDocService) {
        this.typeDocService = typeDocService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TypeDoc.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TypeDoc typeDoc = (TypeDoc) o;

        String field = searchNameFieldInParentEntity(errors, typeDoc.getClass());

        if (typeDoc.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У типа документа отсутствует id!");

        } else if (typeDocService.findById(typeDoc.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Типа документа с таким id не существует!");
        }
    }
}

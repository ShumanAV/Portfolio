package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;
import ru.shuman.Project_Aibolit_Server.services.TypeDocService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

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

        String field = StandardMethods.searchNameFieldInTargetClass(errors, typeDoc.getClass());

        if (typeDoc.getName() == null || typeDoc.getName().equals("")) {
            errors.rejectValue(field == null ? "name" : field, "", "У типа документа отсутствует наименование!");

        } else if (typeDocService.findByName(typeDoc.getName()).isEmpty()) {
            errors.rejectValue(field == null ? "name" : field, "", "Типа документа с таким наименованием не существует!");
        }
    }
}

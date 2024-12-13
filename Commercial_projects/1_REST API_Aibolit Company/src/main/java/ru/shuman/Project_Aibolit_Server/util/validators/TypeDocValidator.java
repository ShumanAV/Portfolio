package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;
import ru.shuman.Project_Aibolit_Server.services.TypeDocService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class TypeDocValidator implements Validator {

    private final TypeDocService typeDocService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeDocValidator(TypeDocService typeDocService) {
        this.typeDocService = typeDocService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TypeDoc.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TypeDoc typeDoc = (TypeDoc) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, typeDoc.getClass());

        //проверяем есть ли название типа документа
        if (typeDoc.getName() != null) {
            //проверяем уникальность названия типа документа, есть ли уже тип документа с таким названием и другим id
            Optional<TypeDoc> existingTypeDoc = typeDocService.findByName(typeDoc.getName());
            if (existingTypeDoc.isPresent() && typeDoc.getId() != existingTypeDoc.get().getId()) {
                errors.rejectValue(field == null ? "name" : field, "", "Тип документа с таким названием уже существует!");
            }
        }
    }
}

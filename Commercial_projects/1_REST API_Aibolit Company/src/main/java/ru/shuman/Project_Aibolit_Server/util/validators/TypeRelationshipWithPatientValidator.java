package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeEmployment;
import ru.shuman.Project_Aibolit_Server.models.TypeRelationshipWithPatient;
import ru.shuman.Project_Aibolit_Server.services.TypeRelationshipWithPatientService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class TypeRelationshipWithPatientValidator implements Validator {

    private final TypeRelationshipWithPatientService typeRelationshipWithPatientService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeRelationshipWithPatientValidator(TypeRelationshipWithPatientService typeRelationshipWithPatientService) {
        this.typeRelationshipWithPatientService = typeRelationshipWithPatientService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TypeRelationshipWithPatient.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TypeRelationshipWithPatient typeRelationshipWithPatient = (TypeRelationshipWithPatient) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, typeRelationshipWithPatient.getClass());

        //проверяем есть ли название типа отношений родителя с пациентом
        if (typeRelationshipWithPatient.getName() != null) {
            //проверяем уникальность названия типа отношений родителя с пациентом, есть ли уже тип отношений родителя
            // с пациентом с таким названием и другим id
            Optional<TypeRelationshipWithPatient> existingTypeRelationshipWithPatient =
                    typeRelationshipWithPatientService.findByName(typeRelationshipWithPatient.getName());
            if (existingTypeRelationshipWithPatient.isPresent()
                    && typeRelationshipWithPatient.getId() != existingTypeRelationshipWithPatient.get().getId()) {
                errors.rejectValue(field == null ? "name" : field, "", "Тип отношения родителя с пациентом с таким названием уже существует!");
            }
        }
    }
}

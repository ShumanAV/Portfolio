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

        String field = searchNameFieldInParentEntity(errors, typeRelationshipWithPatient.getClass());

        Optional<TypeRelationshipWithPatient> existingTypeRelationshipWithPatient =
                typeRelationshipWithPatientService.findByName(typeRelationshipWithPatient.getName());
        if (existingTypeRelationshipWithPatient.isPresent()
                && typeRelationshipWithPatient.getId() != existingTypeRelationshipWithPatient.get().getId()) {
            errors.rejectValue(field == null ? "name" : field, "", "Тип отношения родителя с пациентом с таким названием уже существует!");
        }
    }
}

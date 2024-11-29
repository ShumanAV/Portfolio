package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeRelationshipWithPatient;
import ru.shuman.Project_Aibolit_Server.services.TypeRelationshipWithPatientService;

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
    }
}

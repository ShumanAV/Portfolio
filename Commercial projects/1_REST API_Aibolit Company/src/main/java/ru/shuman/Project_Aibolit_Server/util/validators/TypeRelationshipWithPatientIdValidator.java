package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.TypeRelationshipWithPatient;
import ru.shuman.Project_Aibolit_Server.services.TypeRelationshipWithPatientService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class TypeRelationshipWithPatientIdValidator implements Validator {

    private final TypeRelationshipWithPatientService typeRelationshipWithPatientService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeRelationshipWithPatientIdValidator(TypeRelationshipWithPatientService typeRelationshipWithPatientService) {
        this.typeRelationshipWithPatientService = typeRelationshipWithPatientService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TypeRelationshipWithPatient.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TypeRelationshipWithPatient typeRelationship = (TypeRelationshipWithPatient) o;

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, typeRelationship.getClass());

        //проверяем есть ли id у типа отношений родителя с пациентом
        if (typeRelationship.getId() == null) {
            errors.rejectValue(field == null ? "id": field, "", "У типа отношений родителя с пациентом отсутствует id!");

            //проверяем есть ли тип отношений родителя с пациентом в БД с таким id
        } else if (typeRelationshipWithPatientService.findById(typeRelationship.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Тип отношения родителя с пациентом с таким id не существует!");
        }
    }
}

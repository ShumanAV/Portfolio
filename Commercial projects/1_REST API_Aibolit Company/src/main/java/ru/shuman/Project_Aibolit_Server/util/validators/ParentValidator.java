package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.services.ParentService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.Optional;

@Component
public class ParentValidator implements Validator {

    private final ParentService parentService;
    private final DocumentValidator documentValidator;
    private final DocumentIdValidator documentIdValidator;
    private final AddressValidator addressValidator;
    private final AddressIdValidator addressIdValidator;
    private final TypeRelationshipWithPatientIdValidator typeRelationshipWithPatientIdValidator;
    private final TypeRelationshipWithPatientValidator typeRelationshipWithPatientValidator;
    private final EducationIdValidator educationIdValidator;
    private final EducationValidator educationValidator;
    private final BloodIdValidator bloodIdValidator;
    private final BloodValidator bloodValidator;
    private final TypeEmploymentIdValidator typeEmploymentIdValidator;
    private final TypeEmploymentValidator typeEmploymentValidator;
    private final GenderIdValidator genderIdValidator;
    private final GenderValidator genderValidator;

    @Autowired
    public ParentValidator(ParentService parentService, DocumentValidator documentValidator, DocumentIdValidator documentIdValidator, AddressValidator addressValidator,
                           AddressIdValidator addressIdValidator, TypeRelationshipWithPatientIdValidator typeRelationshipWithPatientIdValidator,
                           TypeRelationshipWithPatientValidator typeRelationshipWithPatientValidator,
                           EducationIdValidator educationIdValidator, EducationValidator educationValidator, BloodIdValidator bloodIdValidator,
                           BloodValidator bloodValidator, TypeEmploymentIdValidator typeEmploymentIdValidator,
                           TypeEmploymentValidator typeEmploymentValidator, GenderIdValidator genderIdValidator, GenderValidator genderValidator) {
        this.parentService = parentService;
        this.documentIdValidator = documentIdValidator;
        this.addressIdValidator = addressIdValidator;
        this.typeRelationshipWithPatientIdValidator = typeRelationshipWithPatientIdValidator;
        this.typeRelationshipWithPatientValidator = typeRelationshipWithPatientValidator;
        this.documentValidator = documentValidator;
        this.addressValidator = addressValidator;
        this.educationIdValidator = educationIdValidator;
        this.educationValidator = educationValidator;
        this.bloodIdValidator = bloodIdValidator;
        this.bloodValidator = bloodValidator;
        this.typeEmploymentIdValidator = typeEmploymentIdValidator;
        this.typeEmploymentValidator = typeEmploymentValidator;
        this.genderIdValidator = genderIdValidator;
        this.genderValidator = genderValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Parent.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Parent parent = (Parent) o;

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, parent.getClass());

        if (parent.getDocument() != null) {
            if (parent.getId() == null && parent.getDocument().getId() != null) {
                errors.rejectValue(field == null ? "document" : field, "", "У нового родителя указан существующий документ с id!");
            }
            if (parent.getId() != null && parent.getDocument().getId() == null) {
                errors.rejectValue(field == null ? "document" : field, "", "У существующего родителя указан новый документ без id!");
            }
            if (parent.getDocument().getId() != null) {
                documentIdValidator.validate(parent.getDocument(), errors);
            }
            documentValidator.validate(parent.getDocument(), errors);
        }

        if (parent.getAddress() != null) {
            if (parent.getId() == null && parent.getAddress().getId() != null) {
                errors.rejectValue(field == null ? "address" : field, "", "У нового родителя указан существующий адрес с id!");
            }
            if (parent.getId() != null && parent.getAddress().getId() == null) {
                errors.rejectValue(field == null ? "address" : field, "", "У существующего родителя указан новый адрес без id!");
            }
            if (parent.getAddress().getId() != null) {
                addressIdValidator.validate(parent.getAddress(), errors);
            }
            addressValidator.validate(parent.getAddress(), errors);
        }

        Optional<Parent> existingParent = parentService.findByPhone(parent.getPhone());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "phone" : field, "", "Родитель с таким номером телефона уже существует!");
        }

        existingParent = parentService.findByEmail(parent.getEmail());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "email" : field, "", "Родитель с таким адресом электронной почты уже существует!");
        }

        existingParent = parentService.findByPolicy(parent.getPolicy());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "policy" : field, "", "Родитель с таким номером полиса уже существует!");
        }

        existingParent = parentService.findBySnils(parent.getSnils());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "snils" : field, "", "Родитель с таким номером СНИЛС уже существует!");
        }

        existingParent = parentService.findByInn(parent.getInn());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "inn" : field, "", "Родитель с таким номером ИНН уже существует!");
        }

        if (parent.getTypeRelationshipWithPatient() != null) {
            typeRelationshipWithPatientIdValidator.validate(parent.getTypeRelationshipWithPatient(), errors);
            typeRelationshipWithPatientValidator.validate(parent.getTypeRelationshipWithPatient(), errors);
        }

        if (parent.getEducation() != null) {
            educationIdValidator.validate(parent.getEducation(), errors);
            educationValidator.validate(parent.getEducation(), errors);
        }

        if (parent.getBlood() != null) {
            bloodIdValidator.validate(parent.getBlood(), errors);
            bloodValidator.validate(parent.getBlood(), errors);
        }

        if (parent.getTypeEmployment() != null) {
            typeEmploymentIdValidator.validate(parent.getTypeEmployment(), errors);
            typeEmploymentValidator.validate(parent.getTypeEmployment(), errors);
        }

        if (parent.getGender() != null) {
            genderIdValidator.validate(parent.getGender(), errors);
            genderValidator.validate(parent.getGender(), errors);
        }

    }
}

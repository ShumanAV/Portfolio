package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.services.ParentService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public ParentValidator(ParentService parentService, DocumentValidator documentValidator, DocumentIdValidator documentIdValidator,
                           AddressValidator addressValidator, AddressIdValidator addressIdValidator,
                           TypeRelationshipWithPatientIdValidator typeRelationshipWithPatientIdValidator,
                           TypeRelationshipWithPatientValidator typeRelationshipWithPatientValidator,
                           EducationIdValidator educationIdValidator, EducationValidator educationValidator,
                           BloodIdValidator bloodIdValidator, BloodValidator bloodValidator,
                           TypeEmploymentIdValidator typeEmploymentIdValidator, TypeEmploymentValidator typeEmploymentValidator,
                           GenderIdValidator genderIdValidator, GenderValidator genderValidator) {
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

        //находим название поля в родительской сущности, к которому относится текущая сущность
        String field = searchNameFieldInParentEntity(errors, parent.getClass());

        //проверяем уникальность номера телефона родителя, есть ли уже родитель с таким номером телефона в БД
        Optional<Parent> existingParent = parentService.findByPhone(parent.getPhone());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "phone" : field, "", "Родитель с таким номером телефона уже существует!");
        }

        //проверяем уникальность адреса электронной почты родителя, есть ли уже родитель с такой электронной почтой в БД
        existingParent = parentService.findByEmail(parent.getEmail());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "email" : field, "", "Родитель с таким адресом электронной почты уже существует!");
        }

        //проверяем уникальность номера полиса родителя, есть ли уже родитель с таким номером полиса в БД
        existingParent = parentService.findByPolicy(parent.getPolicy());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "policy" : field, "", "Родитель с таким номером полиса уже существует!");
        }

        //проверяем уникальность номера СНИЛС родителя, есть ли уже родитель с таким номером СНИЛС в БД
        existingParent = parentService.findBySnils(parent.getSnils());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "snils" : field, "", "Родитель с таким номером СНИЛС уже существует!");
        }

        //проверяем уникальность ИНН родителя, есть ли уже родитель с таким ИНН в БД
        existingParent = parentService.findByInn(parent.getInn());
        if (existingParent.isPresent() && parent.getId() != existingParent.get().getId()) {
            errors.rejectValue(field == null ? "inn" : field, "", "Родитель с таким номером ИНН уже существует!");
        }

        //проверяем если документ подтверждения личности у родителя не null (чтобы не было NullPointerException),
        // далее если id у документа не null, то валидируем id документа, и далее валидируем сам документ,
        // т.к. он может быть создан новый или изменен существующий
        if (parent.getDocument() != null) {
            if (parent.getDocument().getId() != null) {
                documentIdValidator.validate(parent.getDocument(), errors);
            }
            documentValidator.validate(parent.getDocument(), errors);
        }

        //проверяем если адрес проживания у родителя не null (чтобы не было NullPointerException),
        // далее если id у адреса не null, то валидируем id адреса, и далее валидируем сам адрес,
        // т.к. он может быть создан новый или изменен существующий
        if (parent.getAddress() != null) {
            if (parent.getAddress().getId() != null) {
                addressIdValidator.validate(parent.getAddress(), errors);
            }
            addressValidator.validate(parent.getAddress(), errors);
        }

        //проверяем если тип отношения родителя с пациентом у родителя не null (чтобы не было NullPointerException)
        // и если id не null, то валидируем id у типа отношения родителя с пациентом, сам тип отношения родителя
        // с пациентом валидировался при создании
        if (parent.getTypeRelationshipWithPatient() != null && parent.getTypeRelationshipWithPatient().getId() != null) {
                typeRelationshipWithPatientIdValidator.validate(parent.getTypeRelationshipWithPatient(), errors);
        }

        //проверяем если образование у родителя не null (чтобы не было NullPointerException) и если id образования не null,
        // то валидируем id у образования, само образования валидировалось при создании
        if (parent.getEducation() != null && parent.getEducation().getId() != null) {
            educationIdValidator.validate(parent.getEducation(), errors);
        }

        //проверяем если группа крови у родителя не null (чтобы не было NullPointerException) и если id у группы крови не null,
        // то валидируем id у группы крови, сама группа крови валидировалась при создании
        if (parent.getBlood() != null && parent.getBlood().getId() != null) {
            bloodIdValidator.validate(parent.getBlood(), errors);
        }

        //проверяем если тип занятости у родителя не null (чтобы не было NullPointerException) и если id у типа занятости не null,
        // то валидируем id у типа занятости, сам тип занятости валидировался при создании
        if (parent.getTypeEmployment() != null && parent.getTypeEmployment().getId() != null) {
            typeEmploymentIdValidator.validate(parent.getTypeEmployment(), errors);
        }

        //проверяем если гендер у родителя не null (чтобы не было NullPointerException) и если id у гендера не null,
        // то валидируем id у гендера, сам гендер валидировался при создании
        if (parent.getGender() != null && parent.getGender().getId() != null) {
            genderIdValidator.validate(parent.getGender(), errors);
        }

    }
}

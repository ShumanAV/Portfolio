package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.services.PatientService;

import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class PatientValidator implements Validator {

    private final PatientService patientService;
    private final PlaceStudyValidator placeStudyValidator;
    private final PlaceStudyIdValidator placeStudyIdValidator;
    private final DocumentValidator documentValidator;
    private final DocumentIdValidator documentIdValidator;
    private final AddressValidator addressValidator;
    private final AddressIdValidator addressIdValidator;
    private final BloodIdValidator bloodIdValidator;
    private final BloodValidator bloodValidator;
    private final GenderIdValidator genderIdValidator;
    private final GenderValidator genderValidator;
    private final ParentValidator parentValidator;
    private final ParentIdValidator parentIdValidator;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PatientValidator(PatientService patientService, PlaceStudyValidator placeStudyValidator, PlaceStudyIdValidator placeStudyIdValidator, DocumentValidator documentValidator,
                            DocumentIdValidator documentIdValidator, AddressValidator addressValidator, AddressIdValidator addressIdValidator, BloodIdValidator bloodIdValidator, BloodValidator bloodValidator,
                            GenderIdValidator genderIdValidator, GenderValidator genderValidator, ParentValidator parentValidator, ParentIdValidator parentIdValidator) {
        this.patientService = patientService;
        this.placeStudyValidator = placeStudyValidator;
        this.placeStudyIdValidator = placeStudyIdValidator;
        this.documentValidator = documentValidator;
        this.documentIdValidator = documentIdValidator;
        this.addressValidator = addressValidator;
        this.addressIdValidator = addressIdValidator;
        this.bloodIdValidator = bloodIdValidator;
        this.bloodValidator = bloodValidator;
        this.genderIdValidator = genderIdValidator;
        this.genderValidator = genderValidator;
        this.parentValidator = parentValidator;
        this.parentIdValidator = parentIdValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Patient.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Patient patient = (Patient) o;

        //проверяем уникальность номера телефона, отсутствие пользователя с таким номером телефона
        String field = searchNameFieldInParentEntity(errors, patient.getClass());

        //проверяем уникальность номера телефона пациента, есть ли уже пациент с таким номером телефона в БД
        Optional<Patient> existingPatient = patientService.findByPhone(patient.getPhone());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "phone" : field, "", "Пациент с таким номером телефона уже существует!");
        }

        //проверяем уникальность адреса электронной почты пациента, есть ли уже пациент с такой электронной почтой в БД
        existingPatient = patientService.findByEmail(patient.getEmail());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "email" : field, "", "Пациент с таким адресом электронной почты уже существует!");
        }

        //проверяем уникальность номера полиса пациента, есть ли уже пациент с таким номером полиса в БД
        existingPatient = patientService.findByPolicy(patient.getPolicy());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "policy" : field, "", "Пациент с таким номером полиса уже существует!");
        }

        //проверяем уникальность номера СНИЛС пациента, есть ли уже пациент с таким номером СНИЛС в БД
        existingPatient = patientService.findBySnils(patient.getSnils());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "snils" : field, "", "Пациент с таким номером СНИЛС уже существует!");
        }

        //проверяем уникальность ИНН пациента, есть ли уже пациент с таким ИНН в БД
        existingPatient = patientService.findByInn(patient.getInn());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "inn" : field, "", "Пациент с таким ИНН уже существует!");
        }

        //проверяем если место учебы у пациента не null (чтобы не было NullPointerException),
        // далее если id у места учебы не null, то валидируем id места учебы, и далее валидируем само место учебы,
        // т.к. оно может быть создано новое или изменено существующее
        if (patient.getPlaceStudy() != null) {
            if (patient.getPlaceStudy().getId() != null) {
                placeStudyIdValidator.validate(patient.getPlaceStudy(), errors);
            }
            placeStudyValidator.validate(patient.getPlaceStudy(), errors);
        }

        //проверяем если документ подтверждения личности у пациента не null (чтобы не было NullPointerException),
        // далее если id у документа не null, то валидируем id документа, и далее валидируем сам документ,
        // т.к. он может быть создан новый или изменен существующий
        if (patient.getDocument() != null) {
            if (patient.getDocument().getId() != null) {
                documentIdValidator.validate(patient.getDocument(), errors);
            }
            documentValidator.validate(patient.getDocument(), errors);
        }

        //проверяем если адрес проживания у пациента не null (чтобы не было NullPointerException),
        // далее если id у адреса не null, то валидируем id адреса, и далее валидируем сам адрес,
        // т.к. он может быть создан новый или изменен существующий
        if (patient.getAddress() != null) {
            if (patient.getAddress().getId() != null) {
                addressIdValidator.validate(patient.getAddress(), errors);
            }
            addressValidator.validate(patient.getAddress(), errors);
        }

        //проверяем если группа крови у пациента не null (чтобы не было NullPointerException) и если id у группы крови не null,
        // то валидируем id у группы крови, сама группа крови валидировалась при создании
        if (patient.getBlood() != null && patient.getBlood().getId() != null) {
            bloodIdValidator.validate(patient.getBlood(), errors);
        }

        //проверяем если гендер у пациента не null (чтобы не было NullPointerException) и если id у гендера не null,
        // то валидируем id у гендера, сам гендер валидировался при создании
        if (patient.getGender() != null && patient.getGender().getId() != null) {
            genderIdValidator.validate(patient.getGender(), errors);
        }

        //проверяем если список родителей у пациента не null (чтобы не было NullPointerException), проходимся в цикле
        // по всем родителям для данного пациента, их может быть несколько
        if (patient.getParents() != null) {
            for (Parent parent : patient.getParents()) {
                //проверяем если id у родителя не null, т.е. выбран существующий родитель, то валидируем id родителя,
                // далее валидируем самого родителя
                if (parent.getId() != null) {
                    parentIdValidator.validate(parent, errors);
                }
                parentValidator.validate(parent, errors);
            }
        }
    }
}

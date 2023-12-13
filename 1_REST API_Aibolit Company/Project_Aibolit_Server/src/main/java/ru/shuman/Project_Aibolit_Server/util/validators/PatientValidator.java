package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.services.PatientService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.Optional;

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

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, patient.getClass());

        Optional<Patient> existingPatient = patientService.findByPhone(patient.getPhone());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "phone" : field, "", "Пациент с таким номером телефона уже существует!");
        }

        existingPatient = patientService.findByEmail(patient.getEmail());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "email" : field, "", "Пациент с таким адресом электронной почты уже существует!");
        }

        existingPatient = patientService.findByPolicy(patient.getPolicy());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "policy" : field, "", "Пациент с таким номером полиса уже существует!");
        }

        existingPatient = patientService.findBySnils(patient.getSnils());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "snils" : field, "", "Пациент с таким номером СНИЛС уже существует!");
        }

        existingPatient = patientService.findByInn(patient.getInn());
        if (existingPatient.isPresent() && patient.getId() != existingPatient.get().getId()) {
            errors.rejectValue(field == null ? "inn" : field, "", "Пациент с таким номером ИНН уже существует!");
        }

        if (patient.getPlaceStudy() == null) {
            errors.rejectValue(field == null ? "placeStudy" : field, "", "Место учебы у пациента отсутствует!");
        } else {
            if (patient.getPlaceStudy().getId() != null) {
                placeStudyIdValidator.validate(patient.getPlaceStudy(), errors);
            }
            placeStudyValidator.validate(patient.getPlaceStudy(), errors);
        }

        if (patient.getDocument() == null) {
            errors.rejectValue(field == null ? "document" : field, "", "Документ у пациента отсутствует!");
        } else {
            if (patient.getDocument().getId() != null) {
                documentIdValidator.validate(patient.getDocument(), errors);
            }
            documentValidator.validate(patient.getDocument(), errors);
        }

        if (patient.getAddress() == null) {
            errors.rejectValue(field == null ? "address" : field, "", "Адрес у пациента отсутствует!");
        } else {
            if (patient.getAddress().getId() != null) {
                addressIdValidator.validate(patient.getAddress(), errors);
            }
            addressValidator.validate(patient.getAddress(), errors);
        }

        if (patient.getId() == null) {
            if (patient.getPlaceStudy().getId() != null) {
                errors.rejectValue(field == null ? "placeStudy" : field, "", "У нового пациента указано существующее место учебы с id!");
            }
            if (patient.getDocument().getId() != null) {
                errors.rejectValue(field == null ? "document" : field, "", "У нового пациента указан существующий документ с id!");
            }
            if (patient.getAddress().getId() != null) {
                errors.rejectValue(field == null ? "address" : field, "", "У нового пациента указан существующий адрес с id!");
            }
        } else {
            if (patient.getPlaceStudy().getId() == null) {
                errors.rejectValue(field == null ? "placeStudy" : field, "", "У существующего пациента указано новое место учебы без id!");
            }
            if (patient.getDocument().getId() == null) {
                errors.rejectValue(field == null ? "document" : field, "", "У существующего пациента указан новый документ без id!");
            }
            if (patient.getAddress().getId() == null) {
                errors.rejectValue(field == null ? "address" : field, "", "У существующего пациента указан новый адрес без id!");
            }
        }

        if (patient.getBlood() != null) {
            bloodIdValidator.validate(patient.getBlood(), errors);
            bloodValidator.validate(patient.getBlood(), errors);
        }

        if (patient.getGender() != null) {
            genderIdValidator.validate(patient.getGender(), errors);
            genderValidator.validate(patient.getGender(), errors);
        }

        for (Parent parent : patient.getParents()) {
            if (parent.getId() != null) {
                parentIdValidator.validate(parent, errors);
            }
            parentValidator.validate(parent, errors);
        }

    }
}

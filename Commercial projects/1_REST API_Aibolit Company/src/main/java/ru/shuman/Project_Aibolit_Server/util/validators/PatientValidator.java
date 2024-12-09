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

        String field = searchNameFieldInParentEntity(errors, patient.getClass());

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
            errors.rejectValue(field == null ? "inn" : field, "", "Пациент с таким ИНН уже существует!");
        }

        if (patient.getPlaceStudy() != null) {
            if (patient.getPlaceStudy().getId() != null) {
                placeStudyIdValidator.validate(patient.getPlaceStudy(), errors);
            }
            placeStudyValidator.validate(patient.getPlaceStudy(), errors);
        }

        if (patient.getDocument() != null) {
            if (patient.getDocument().getId() != null) {
                documentIdValidator.validate(patient.getDocument(), errors);
            }
            documentValidator.validate(patient.getDocument(), errors);
        }

        if (patient.getAddress() != null) {
            if (patient.getAddress().getId() != null) {
                addressIdValidator.validate(patient.getAddress(), errors);
            }
            addressValidator.validate(patient.getAddress(), errors);
        }

        if (patient.getBlood() != null) {
            bloodIdValidator.validate(patient.getBlood(), errors);
        }

        if (patient.getGender() != null) {
            genderIdValidator.validate(patient.getGender(), errors);
        }

        for (Parent parent : patient.getParents()) {
            if (parent.getId() != null) {
                parentIdValidator.validate(parent, errors);
            }
            parentValidator.validate(parent, errors);
        }

    }
}

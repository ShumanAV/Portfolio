package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.services.CallingService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class CallingValidator implements Validator {

    private final CallingService callingService;
    private final DoctorValidator doctorValidator;
    private final DoctorIdValidator doctorIdValidator;
    private final PatientValidator patientValidator;
    private final PatientIdValidator patientIdValidator;
    private final JournalValidator journalValidator;
    private final JournalIdValidator journalIdValidator;
    private final PriceValidator priceValidator;
    private final PriceIdValidator priceIdValidator;

    @Autowired
    public CallingValidator(CallingService callingService, PatientValidator patientValidator, DoctorValidator doctorValidator,
                            DoctorIdValidator doctorIdValidator, PatientIdValidator patientIdValidator, JournalValidator journalValidator, JournalIdValidator journalIdValidator, PriceValidator priceValidator,
                            PriceIdValidator priceIdValidator) {
        this.callingService = callingService;
        this.patientValidator = patientValidator;
        this.doctorValidator = doctorValidator;
        this.doctorIdValidator = doctorIdValidator;
        this.patientIdValidator = patientIdValidator;
        this.journalValidator = journalValidator;
        this.journalIdValidator = journalIdValidator;
        this.priceValidator = priceValidator;
        this.priceIdValidator = priceIdValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Calling.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Calling calling = (Calling) o;

        String field = searchNameFieldInParentEntity(errors, calling.getClass());

        if (calling.getDoctor() == null) {
            errors.rejectValue(field == null ? "user": field, "", "Пользователь не выбран!");
        } else {
            doctorIdValidator.validate(calling.getDoctor(), errors);
            doctorValidator.validate(calling.getDoctor(), errors);
        }

        if (calling.getJournal() == null) {
            errors.rejectValue(field == null ? "diary": field, "", "Дневник не выбран!");
        } else {
            if (calling.getJournal().getId() != null) {
                journalIdValidator.validate(calling.getJournal(), errors);
            }
            journalValidator.validate(calling.getJournal(), errors);
        }

        if (calling.getPrice() == null) {
            errors.rejectValue(field == null ? "price": field, "", "Прайс не выбран!");
        } else {
            priceIdValidator.validate(calling.getPrice(), errors);
            priceValidator.validate(calling.getPrice(), errors);
        }

        if (calling.getPatient() == null) {
            errors.rejectValue(field == null ? "patient": field, "", "Пациент не выбран!");
        } else {
            if (calling.getPatient().getId() != null) {
                patientIdValidator.validate(calling.getPatient(), errors);
            }
            patientValidator.validate(calling.getPatient(), errors);
        }
    }
}

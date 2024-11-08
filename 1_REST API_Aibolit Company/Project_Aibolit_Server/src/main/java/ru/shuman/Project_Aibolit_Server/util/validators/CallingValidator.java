package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.services.CallingService;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

@Component
public class CallingValidator implements Validator {

    private final CallingService callingService;
    private final UserValidator userValidator;
    private final UserIdValidator userIdValidator;
    private final PatientValidator patientValidator;
    private final PatientIdValidator patientIdValidator;
    private final DiaryValidator diaryValidator;
    private final DiaryIdValidator diaryIdValidator;
    private final PriceValidator priceValidator;
    private final PriceIdValidator priceIdValidator;

    @Autowired
    public CallingValidator(CallingService callingService, PatientValidator patientValidator, UserValidator userValidator,
                            UserIdValidator userIdValidator, PatientIdValidator patientIdValidator, DiaryValidator diaryValidator, DiaryIdValidator diaryIdValidator, PriceValidator priceValidator,
                            PriceIdValidator priceIdValidator) {
        this.callingService = callingService;
        this.patientValidator = patientValidator;
        this.userValidator = userValidator;
        this.userIdValidator = userIdValidator;
        this.patientIdValidator = patientIdValidator;
        this.diaryValidator = diaryValidator;
        this.diaryIdValidator = diaryIdValidator;
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

        String field = GeneralMethods.searchNameFieldInTargetClass(errors, calling.getClass());

        if (calling.getUser() == null) {
            errors.rejectValue(field == null ? "user": field, "", "Пользователь не выбран!");
        } else {
            userIdValidator.validate(calling.getUser(), errors);
            userValidator.validate(calling.getUser(), errors);
        }

        if (calling.getDiary() == null) {
            errors.rejectValue(field == null ? "diary": field, "", "Дневник не выбран!");
        } else {
            if (calling.getDiary().getId() != null) {
                diaryIdValidator.validate(calling.getDiary(), errors);
            }
            diaryValidator.validate(calling.getDiary(), errors);
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

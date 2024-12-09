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

    /*
    Внедрение зависимостей
     */
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

        //проверяем если id доктора есть, валидируем его id, сам доктор валидировался при создании
        if (calling.getDoctor().getId() != null) {
            doctorIdValidator.validate(calling.getDoctor(), errors);
        }

        //проверяем если есть id карточки, валидируем ее id, при создании вызова создается новая карточка, поэтому
        // ее id будет null, а не null будет только при редактировании
        if (calling.getJournal().getId() != null) {
            journalIdValidator.validate(calling.getJournal(), errors);
        }
        //валидируем карточку вызова
        journalValidator.validate(calling.getJournal(), errors);

        //проверяем если есть id прайса, валидируем его id, сам прайс валидировался при создании
        if (calling.getPrice().getId() != null) {
            priceIdValidator.validate(calling.getPrice(), errors);
        }

        //проверяем если есть id пациента, валидируем его id, это значит что выбран существующий пациент
        if (calling.getPatient().getId() != null) {
            patientIdValidator.validate(calling.getPatient(), errors);
        }
        //валидируем самого пациента, на случай если он был создан новый или изменен существующий
        patientValidator.validate(calling.getPatient(), errors);
    }
}

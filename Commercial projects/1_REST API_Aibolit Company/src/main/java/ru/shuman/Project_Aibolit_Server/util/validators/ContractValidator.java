package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.services.ContractService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class ContractValidator implements Validator {

    private final ContractService contractService;
    private final DoctorValidator doctorValidator;
    private final DoctorIdValidator doctorIdValidator;
    private final PatientValidator patientValidator;
    private final PatientIdValidator patientIdValidator;
    private final TypeContractIdValidator typeContractIdValidator;
    private final TypeContractValidator typeContractValidator;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public ContractValidator(ContractService contractService, PatientValidator patientValidator, DoctorValidator doctorValidator,
                             DoctorIdValidator doctorIdValidator, PatientIdValidator patientIdValidator, TypeContractIdValidator typeContractIdValidator,
                             TypeContractValidator typeContractValidator) {
        this.contractService = contractService;
        this.patientValidator = patientValidator;
        this.doctorValidator = doctorValidator;
        this.doctorIdValidator = doctorIdValidator;
        this.patientIdValidator = patientIdValidator;
        this.typeContractIdValidator = typeContractIdValidator;
        this.typeContractValidator = typeContractValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Contract.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contract contract = (Contract) o;

        //проверяем если id доктора есть, валидируем его id, сам доктор валидровался при создании
        if (contract.getDoctor().getId() != null) {
            doctorIdValidator.validate(contract.getDoctor(), errors);
        }

        //проверяем если id пациента есть, валидируем его id
        if (contract.getPatient().getId() != null) {
            patientIdValidator.validate(contract.getPatient(), errors);
        }
        //валидируем самого пациента, он может быть создан новый либо изменен существующий
        patientValidator.validate(contract.getPatient(), errors);

        //проверяем если id типа договора есть, валидируем его id, сам тип договора валидровался при создании
        if (contract.getTypeContract().getId() != null) {
            typeContractIdValidator.validate(contract.getTypeContract(), errors);
        }
    }
}

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

        String field = searchNameFieldInParentEntity(errors, contract.getClass());

        if (contract.getDoctor() == null) {
            errors.rejectValue(field == null ? "user": field, "", "Пользователь в договоре не выбран!");
        } else {
            doctorIdValidator.validate(contract.getDoctor(), errors);
            doctorValidator.validate(contract.getDoctor(), errors);
        }

        if (contract.getPatient() == null) {
            errors.rejectValue(field == null ? "patient": field, "", "Пациент в договоре не выбран!");
        } else {
            if (contract.getPatient().getId() != null) {
                patientIdValidator.validate(contract.getPatient(), errors);
            }
            patientValidator.validate(contract.getPatient(), errors);
        }

        if (contract.getTypeContract() == null) {
            errors.rejectValue(field == null ? "typeContract": field, "", "Тип договора в договоре не выбран!");
        } else {
            typeContractIdValidator.validate(contract.getTypeContract(), errors);
            typeContractValidator.validate(contract.getTypeContract(), errors);
        }
    }
}

package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.services.ContractService;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

@Component
public class ContractIdValidator implements Validator {

    private final ContractService contractService;

    @Autowired
    public ContractIdValidator(ContractService contractService) {
        this.contractService = contractService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Contract.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contract contract = (Contract) o;

        String field = StandardMethods.searchNameFieldInTargetClass(errors, contract.getClass());

        if (contract.getId() == null) {
            errors.rejectValue(field == null ? "id" : field, "", "У договора отсутствует id!");

        } else if (contractService.findById(contract.getId()).isEmpty()) {
            errors.rejectValue(field == null ? "id": field, "", "Договора с таким id не существует!");
        }
    }
}

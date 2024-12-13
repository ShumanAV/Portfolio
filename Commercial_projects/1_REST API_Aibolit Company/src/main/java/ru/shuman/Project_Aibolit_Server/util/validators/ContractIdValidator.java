package ru.shuman.Project_Aibolit_Server.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.services.ContractService;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.searchNameFieldInParentEntity;

@Component
public class ContractIdValidator implements Validator {

    private final ContractService contractService;

    /*
    Внедрение зависимостей
     */
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

        //проверяем есть ли id у договора
        if (contract.getId() == null) {
            errors.rejectValue("id", "", "У договора отсутствует id!");

            //если id есть, проверяем есть ли договор в БД с таким id
        } else if (contractService.findById(contract.getId()).isEmpty()) {
            errors.rejectValue("id", "", "Договора с таким id не существует!");
        }
    }
}
